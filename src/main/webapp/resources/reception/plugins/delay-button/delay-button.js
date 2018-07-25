/*
* 作用：延时按钮，按下按钮后，需延时指定时间才能继续点击
* 用法：1、引入该js文件。  2、在按钮的class中增加类名 disabled-*s  ("*"标识要延时几秒)
* Author：0xReturn 
* 2014-7-29
*/

;(function(window){

	// 事件代理
	window.addEventListener("click",function(e){

		// 筛选满足条件的按钮
		if ( (e.target.tagName.toLowerCase() === 'input') && (e.target.className.indexOf('disabled-') > -1) 
			|| (e.target.tagName.toLowerCase() === 'button') && (e.target.className.indexOf('disabled-') > -1) ){

			var targetClassName = e.target.className;  // 所有类组合拼接的字符串
			var targetDisabled  = '';                  // 最终剥离出的类名

			var oneClass = targetClassName.indexOf(' ');
			// 只有一个类的情况
			if(oneClass < 0){ 
				var index_start_oneclass = e.target.className.indexOf('disabled-');
				var onlyOneClassName     = targetClassName.substring(index_start_oneclass,targetClassName.length)
				targetDisabled           = onlyOneClassName;
			}else{ // 多个类的情况
				var index_start = targetClassName.indexOf('disabled-');
				var index_end   = targetClassName.indexOf(' ',index_start);
				
				if(index_end == -1){ // 多个类，且目标类在最后的情况
					targetDisabled = targetClassName.substring(index_start);
				}else{               // 多各类，且目标类在中间/头部的情况
					targetDisabled  = targetClassName.substring(index_start,index_end);
				}
			}

			// 剥离延时时间
			var index_disstart  = targetDisabled.indexOf('-') + 1;
			var index_dissend   = targetDisabled.lastIndexOf('s');
			var disabledTime    = targetDisabled.substring(index_disstart,index_dissend);

			var targetTimerName = e.target.getAttribute('data-timerid');

			if(targetTimerName){

				(function(targetTimerName,targetNode){
					targetNode.disabled = true;
					targetTimerName = setTimeout(function(){
						targetNode.disabled = false;
						targetTimerName = null;
					},disabledTime * 1000);
				})(targetTimerName,e.target)

			}else{
				var timerid = 'Anran-' + new Date().valueOf();
				e.target.setAttribute('data-timerid',timerid);

				(function(targetTimerName,targetNode){
					targetNode.disabled = true;
					targetTimerName = setTimeout(function(){
						targetNode.disabled = false;
						targetTimerName = null;
					},disabledTime * 1000);
				})(targetTimerName,e.target)
			}
		}
	},false)

})(window)