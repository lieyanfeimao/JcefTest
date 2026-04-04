/**
 * common.js V1.1
 * author:玄翼猫
 */
//加载框索引
var layLoadingIndex;
//弹层公共索引
var layIndex;

/**
 * 公共执行Java方法的函数。此方法返回一个js对象 {"ok":true,"msg":"操作描述","data":JAVA方法返回的数据对象}
 * name:JAVA方法名
 * params:传递的参数。传入json字符串或对象，其他无效，无参数则不传
 * 
 */
function execJava(name,params){
	return new Promise(function(resolve, reject) {
		if(name==null || typeof name!=="string"){
			infoMsg("调用的Java方法名必须是有效的字符串!");
			throw new Error("调用的Java方法名必须是有效的字符串!");
		}
		
		let p={method:name,params:""};
		//传递的参数
		if(params && (typeof params === "object" || typeof params === "string" )){
			if(typeof params === "object") params=JSON.stringify(params);
			p.params=params;
		}
		
		window.java({
			request:JSON.stringify(p)
			,persistent:true
			,onSuccess:function(response){
				hideLoading();
				// console.log(response);
				resolve(JSON.parse(response));
			}
			,onFailure:function(code,response){
				hideLoading();

				if(code==-1){//系统发送过来的错误信息
					let data=JSON.parse(response);
					infoMsg(data.msg.replace(/(\r\n)|(\n)/g,'<br/>'));
				}
				
				throw new Error("系统错误!"+code);
			}
		});
	});
}

//获取参数的值
function getParam(name) {
	let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	let r = window.location.search.substr(1).match(reg);
	let context = "";
	if (r != null)
		context = decodeURIComponent(r[2]);
	reg = null;
	r = null;
	return context == null || context == "" || context == "undefined" ? "" : context;
}

//显示info信息
function infoMsg(msg){
	// $.messager.alert("提示",msg,'info');
	layer.alert(msg);
}


function tipsMsg(msg){
	layer.msg(msg);
}

/**
 * 显示加载遮罩
 */
function showLoading(){
	layLoadingIndex=layer.load(1,{
		shade: [0.1,'#fff'] //0.1透明度的白色背景
	});
}

/**
 * 隐藏加载遮罩
 */
function hideLoading(){
	layer.close(layLoadingIndex);
}

/**
 * 关闭对话框
 */
function closeDialog(){
	layer.close(layIndex);
}

function log(val){
	console.log(JSON.stringify(val));
}