package com.xuanyimao.app.common;

import com.google.gson.Gson;

import com.xuanyimao.app.anno.JsClass;
import com.xuanyimao.app.anno.JsFunction;
import com.xuanyimao.app.anno.JsObject;
import com.xuanyimao.app.entity.js2java.AnnoData;
import com.xuanyimao.app.entity.js2java.JsClassAO;
import com.xuanyimao.app.entity.js2java.JsFunctionAO;
import com.xuanyimao.app.entity.js2java.JsFunctionParam;
import com.xuanyimao.app.util.LogUtil;
import okhttp3.ConnectionPool;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;

/***
 * 应用数据，单例。<br>
 * 此对象中的数据长存于内存中
 * @author liuming
 *
 */
public class ApplicationData {


	/** okhttp 连接池 **/
	public static ConnectionPool connectionPool;



	/**应用程序所在目录*/
	public static String appPath;


	/** 注解数据对象 **/
	public static AnnoData annoData;


	/**
	 * 初始化数据
	 */
	public static void initData(){
		appPath=System.getProperty("user.dir");
		connectionPool=new ConnectionPool(2000, 10, TimeUnit.SECONDS);
		//扫描程序列表
		scannerAnno();

	}


	/**
	 * logo图片路径
	 * @author liuming
	 * @since 2023年8月18日
	 * @return
	 */
	public static String logoImgPath() {
		return appPath+File.separator+Constants.VIEW_FOLDER+File.separator+Constants.LOGO_FILE_NAME;
	}
	
	/**
	 * 首页文件路径
	 * @author liuming
	 * @since 2023年8月18日
	 * @return
	 */
	public static String indexPath() {
		return appPath+File.separator+Constants.VIEW_FOLDER+File.separator+Constants.UI_INDEX_PAGE;
	}

	/**
	 * 扫描注解
	 */
	public static void scannerAnno(){
		Reflections ref = new Reflections("com.xuanyimao.app");


		//JS类
		annoData=new AnnoData();
		List<JsClassAO> jsClassAOList=new ArrayList<JsClassAO>();
		Set<Class<?>> set = ref.getTypesAnnotatedWith(JsClass.class);
		for (Class<?> c : set) {
			try{
				JsClass jsClass=c.getAnnotation(JsClass.class);
				String className=jsClass.name();
				if(StringUtils.isBlank(className)) {
					className=c.getSimpleName();
					className=className.substring(0,1).toLowerCase()+className.substring(1);
				}
				String prefix=jsClass.prefix();
				if(StringUtils.isBlank(prefix)) {
					prefix=className;
				}

				JsClassAO jsClassAO=new JsClassAO(c, c.getDeclaredConstructor().newInstance(), className,c.getName(),prefix);
				jsClassAOList.add(jsClassAO);
			}catch (Exception e){
				LogUtil.getLogger().error("解析 {} 的 @JsClass 注解失败",c,e.getMessage(),e);
			}
		}
		annoData.setAnnoClassList(jsClassAOList);

		Map<String, JsFunctionAO> methodMap=new HashMap<String, JsFunctionAO>();
		//注入对象和解析方法
		for (JsClassAO jsClassAO : jsClassAOList) {
			Class c=jsClassAO.getCls();

			System.out.println("处理的类："+c);

			List<Field> fieldList=new ArrayList<>();
			fieldList.addAll(Arrays.asList(c.getDeclaredFields()));
			//获取继承的父类字段
			fieldList.addAll(findParentFields(c.getSuperclass()));

			if(!fieldList.isEmpty()) {
				for(Field field:fieldList) {
					System.out.println("处理的字段："+field);

					field.setAccessible(true);
					try{
						JsObject jsObject=field.getAnnotation(JsObject.class);
						if(jsObject!=null) {
//							System.out.println(field.getGenericType().getTypeName());
							//为属性赋值，以后根据需要做优化
							for(JsClassAO ac: jsClassAOList) {
								if(field.getGenericType().getTypeName().equals(ac.getClsName())) {//如果与列表的类名一致
//									System.out.println("对象注入："+ac.getClsName());

									field.set(jsClassAO.getObj(), ac.getObj());
									break;
								}
							}
						}
					}catch (Exception e){
						LogUtil.getLogger().error("解析 {} 的字段 {} 的 @JsObject 注解失败",c,field.getName(),e.getMessage(),e);
					}
				}
			}

			Method[] methods=c.getMethods();
			if(methods.length>0) {
				for(Method method:methods) {
					method.setAccessible(true);
					try{
						JsFunction jsFunction=method.getAnnotation(JsFunction.class);
						if(jsFunction!=null) {//方法含有jsFunction注解
							JsFunctionAO jsFunctionAO =new JsFunctionAO(method, jsClassAO);
							//获取方法的所有参数
							Class<?>[] paramClass=method.getParameterTypes();
							if(paramClass.length>0) {//存在参数
								List<JsFunctionParam> paramList=new ArrayList<JsFunctionParam>();
								//使用spring LocalVariableTableParameterNameDiscoverer获取参数名
								ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
								String[] pn=parameterNameDiscoverer.getParameterNames(method);
								for(int j=0;j<paramClass.length;j++) {
//									System.out.println(paramClass[j]+"...."+pn[j]);
									paramList.add(new JsFunctionParam(paramClass[j], pn[j]));
								}
								jsFunctionAO.setMethodParam(paramList);
							}

							String methodName=jsFunction.name();
							if(StringUtils.isBlank(methodName)) {
								methodName=method.getName();
							}
							String funcName=(StringUtils.isNotBlank(jsClassAO.getPrefix())? jsClassAO.getPrefix()+".":"") + methodName;
//							System.out.println("扫描到的JS函数："+funcName);
							jsFunctionAO.setDesc(jsFunction.desc());
							methodMap.put(funcName, jsFunctionAO);

						}
					} catch (Exception e) {
						LogUtil.getLogger().error("解析 {} 的方法 {} 的 @JsFunction 注解失败",c,method.getName(),e.getMessage(),e);
					}
				}
			}
			annoData.setMethodMap(methodMap);

		}


	}

	/**
	 * 查找父类的所有字段
	 * @param c
	 * @return
	 */
	private static List<Field> findParentFields(Class c) {
		List<Field> fieldList=new ArrayList<>();
		while (c!=null) {
			fieldList.addAll(Arrays.asList(c.getDeclaredFields()));
			c=c.getSuperclass();
		}
		return fieldList;
	}

	/**
	 * 获取@JsClass注解的实体对象
	 * @param name
	 * @return
	 */
	public static Object getJsClassInstance(String name) {
		List<JsClassAO> jsClassAOList = annoData.getAnnoClassList();
		if(jsClassAOList !=null && !jsClassAOList.isEmpty()) {
			for(JsClassAO ac: jsClassAOList) {
				if(name.equals(ac.getName())) {
					return ac.getObj();
				}
			}
		}
		return null;
	}
}
