package com.xuanyimao.app.handler;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.xuanyimao.app.common.ApplicationData;
import com.xuanyimao.app.common.Constants;
import com.xuanyimao.app.entity.Message;
import com.xuanyimao.app.entity.js2java.JsFunctionAO;
import com.xuanyimao.app.entity.js2java.JsFunctionParam;
import org.apache.commons.lang3.StringUtils;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.callback.CefQueryCallback;
import org.cef.handler.CefMessageRouterHandlerAdapter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class XstCefMessageRouterHandler extends CefMessageRouterHandlerAdapter{

	//Gson对象
	private static Gson gson=new GsonBuilder()
			.disableHtmlEscaping()
			.serializeNulls()
			.registerTypeAdapter(int.class, new IntTypeAdapter())
			.registerTypeAdapter(Integer.class, new IntTypeAdapter())
			.registerTypeAdapter(long.class, new LongTypeAdapter())
			.registerTypeAdapter(Long.class, new LongTypeAdapter())
			.registerTypeAdapter(double.class, new DoubleTypeAdapter())
			.registerTypeAdapter(Double.class, new DoubleTypeAdapter())
			.registerTypeAdapter(float.class, new FloatTypeAdapter())
			.registerTypeAdapter(Float.class, new FloatTypeAdapter())
			.create();

	@Override
	public void onQueryCanceled(CefBrowser browser, CefFrame frame, long query_id) {
		System.out.println("取消查询:"+query_id);
	}

	/**
	 * 此方法统一返回一个Message对象
	 */
	@Override
	public boolean onQuery(CefBrowser browser, CefFrame frame, long query_id, String request, boolean persistent,
	                       CefQueryCallback callback) {
		try {
			//解析请求参数
			JsonObject jobj=JsonParser.parseString(request).getAsJsonObject();
			//方法名
			String methodName=jobj.get(Constants.JS_METHOD_NAME).getAsString();
			//参数
			String params=jobj.get(Constants.JS_METHOD_PARAM)==null?"":jobj.get(Constants.JS_METHOD_PARAM).getAsString();
//			System.out.println(methodName+":"+params);
			JsFunctionAO jsFunctionAO = ApplicationData.annoData.getMethodMap().get(methodName);
			if(jsFunctionAO ==null) {
				callback.failure(-1,gson.toJson(Message.error(methodName+" 函数未在Java代码中声明，调用失败!")));
				return true;
			}
			//解析参数
			if(StringUtils.isNotBlank(params)) {
				jobj=JsonParser.parseString(params).getAsJsonObject();
			}
//			System.out.println(jobj);

			//获取方法的参数列表
			List<JsFunctionParam> jsFunctionParam = jsFunctionAO.getMethodParam();
			Method method= jsFunctionAO.getMethod();
			method.setAccessible(true);
			Object result=null;

			//参数中是否包含CefQueryCallback,如果包含,则由调用的方法处理回调
			boolean hasCallback=false;
			if(jsFunctionParam ==null || jsFunctionParam.isEmpty()) {//不需要传递参数
				result=method.invoke(jsFunctionAO.getAnnoClass().getObj());
				//    		System.out.println(gson.toJson(result));
			}else {//对传入的参数进行处理
				Object[] objs=new Object[jsFunctionParam.size()];
				//遍历参数数组设置参数值
				for(int i = 0; i< jsFunctionParam.size(); i++) {
					JsFunctionParam mp= jsFunctionParam.get(i);
//	    			System.out.println(mp.getCls());
//	    			System.out.println(mp.getCls()==CefQueryCallback.class);
					if(mp.getCls()==CefQueryCallback.class) {
						objs[i]=callback;
						hasCallback=true;
					}else {
						//按照参数名设置值
//						System.out.println(mp.getName());
//						System.out.println(jobj.get(mp.getName()));

						try{
							if(jobj!=null && jobj.get(mp.getName())!=null) {
								objs[i] = gson.fromJson(jobj.get(mp.getName()), mp.getCls());
							}else {
								//参数可能是一个对象，尝试设置值
								objs[i]=gson.fromJson(jobj, mp.getCls());
							}
						}catch (Exception e) {
							e.printStackTrace();
							objs[i]=null;
						}

					}
				}

				result=method.invoke(jsFunctionAO.getAnnoClass().getObj(),objs);
			}

//	    	if(result !=null && result instanceof Message) {
//	    		callback.success(gson.toJson(result));
//	    	}else {
//	    		callback.success(gson.toJson(Message.success("操作成功", result)));
//	    	}
			if(!hasCallback) callback.success(gson.toJson(result));
		}catch(Exception e) {
			e.printStackTrace();
			callback.failure(-1,gson.toJson(Message.error("系统异常:"+e.getMessage())));
		}
		return true;
	}

	static class IntTypeAdapter extends TypeAdapter<Integer> {
		@Override
		public void write(JsonWriter out, Integer value) throws IOException {
			out.value(value);
		}

		@Override
		public Integer read(JsonReader reader) throws IOException {
			try {
				JsonToken token=reader.peek();

				if (token == JsonToken.NULL) {
					reader.nextNull();
					return null;
				} else if(token==JsonToken.STRING){
					return Integer.parseInt(reader.nextString());
				}
				return reader.nextInt();
			}catch (Exception e){
				return null;
			}
		}
	}

	static class LongTypeAdapter extends TypeAdapter<Long> {
		@Override
		public void write(JsonWriter out, Long value) throws IOException {
			out.value(value);
		}

		@Override
		public Long read(JsonReader reader) throws IOException {
			JsonToken token=reader.peek();
			try {
				if (token == JsonToken.NULL) {
					reader.nextNull();
					return null;
				} else if(token==JsonToken.STRING){
					return Long.parseLong(reader.nextString());
				}
				return reader.nextLong();
			} catch (Exception e) {
				return null;
			}
		}
	}

	static class DoubleTypeAdapter extends TypeAdapter<Double> {
		@Override
		public void write(JsonWriter out, Double value) throws IOException {
			out.value(value);
		}

		@Override
		public Double read(JsonReader reader) throws IOException {
			JsonToken token=reader.peek();
			try {
				if (token == JsonToken.NULL) {
					reader.nextNull();
					return null;
				} else if(token==JsonToken.STRING){
					return Double.parseDouble(reader.nextString());
				}
				return reader.nextDouble();
			} catch (Exception e) {
				return null;
			}
		}
	}

	static class FloatTypeAdapter extends TypeAdapter<Float> {
		@Override
		public void write(JsonWriter out, Float value) throws IOException {
			out.value(value);
		}

		@Override
		public Float read(JsonReader reader) throws IOException {
			JsonToken token=reader.peek();

			try {
				if (token == JsonToken.NULL) {
					reader.nextNull();
					return null;
				}

				return Float.parseFloat(reader.nextString());
			} catch (Exception e) {
				return null;
			}
		}
	}
}
