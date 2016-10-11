$.getScript('js/spin.js');
var ws = new WebSocket("ws://asu-srp:8081/ErzNsk/wsAnswer");
//флаг открытли запрос
var nowCount =0;
var user;
// данные для spinnera
var opts = {
		   lines: 13, // The number of lines to draw
		   length: 25, // The length of each line
		   width: 4, // The line thickness
		   radius: 17, // The radius of the inner circle
		   rotate: 0, // The rotation offset
		   color: '#efefef', // #rgb or #rrggbb
		   speed: 0.75, // Rounds per second
		   trail: 50, // Afterglow percentage
		   shadow: true, // Whether to render a shadow
		   hwaccel: false, // Whether to use hardware acceleration
		   className: 'spinner', // The CSS class to assign to the spinner
		   zIndex: 2e9, // The z-index (defaults to 2000000000)
		   top: 'auto', // Top position relative to parent in px
		   left: 'auto' // Left position relative to parent in px
		};
		var spinner = new Spinner(opts);
		var ajax_cnt = 0; // Support for parallel AJAX requests

ws.onopen = function() {
    console.log("Socket has been opened!");
    postToServerOtladkaXMLNO();
 }

function getNowCount() 
{
	if(nowCount)
	{
		return true;
	}
	else{return false;}
}


ws.onmessage = function(message) {
	if(message.data.indexOf('*')>0) {
		
		document.getElementById("oneTimeTask").value = message.data.substring(0,message.data.indexOf('*'));
		document.getElementById("taskNumber").value = message.data.substring(message.data.indexOf('*')+1);
	} else {
		document.getElementById("wsanswer").textContent += message.data + "\n";
	}
};

function postToServer() {
	ws.send(document.getElementById("msg").value);
	document.getElementById("msg").value = "";
}

function postToServerOtladkaXMLYES() {
	ws.send('xmlotladkazaprosa1');
}

function postToServerOtladkaXMLNO() {
	ws.send('xmlotladkazaprosa0');
}

var datauprmessZP;
ws.onmessage =function(event)
{
		// ложим в текстареа тправленую серером инфу
		var logarea = document.getElementById("tamessage");
	    logarea.value =logarea.value+"\n"+event.data;
	    // если длинна входящего от сервера соккета боль ше 12
	    // условие поставили чтобы не поймать ошибку т.к. сервак отправляет сообщения размером меньше
	    if(event.data.length > 12)
	    var polucheno = event.data.substring(0,9);
	    //Ловим с servlets/WsAnswer.java json данные по которым формировалися upmess
	    // Потом когда придет сообшение получен **************.uprak2 будем выводить на второй лист и третий
	    // в данном случае uprmesszp - это ключ по по которому определяется что эти данные при формировании uprmessa
	    if(event.data.indexOf('.fromdbforuprmess') + 1)
	    {
	    	datauprmessZP= event.data;
	    }
	    
    
    //если сервер отправил файл получен
    if(!event.data.localeCompare('> загружено в эксель') ||   !event.data.localeCompare('> возникла ошибка, проверьте закрыт ли эксель или бд') || !event.data.localeCompare('> режим отладки') || !event.data.localeCompare('q') || !event.data.localeCompare('> пришел'))
    {
    	nowCount= 0;
    //	closeConnect(); //закрываем коннект со стороны клента
    	// сворачиваем окно статус запроса
    	setTimeout ("$('ul#login-dp').slideUp(2000);", 5000);
    	
    	if(!event.data.localeCompare('> загружено в эксель'))
    	{
			    	//
			    	var gouser = "user=" + user;
			    	var hotInstan1 = $('#list1onsc').handsontable('getInstance');
			    	var hotInstan2 = $('#list2onsc').handsontable('getInstance');
			    	var hotInstan3 = $('#list3onsc').handsontable('getInstance');
			    	hotInstan1.clear();
			    	hotInstan2.clear();
			    	hotInstan3.clear();
				    var jqxhr = $.getJSON( "ImportFromExcelToHandsontable",gouser, function(er) 
				    {
				    	hotInstan1.loadData(er.data1);
				    	hotInstan2.loadData(er.data2);
				    	hotInstan3.loadData(er.data3);
				    })
    	}
    	
    }
    if(polucheno == "> получен" )
    {
    	console.log(datauprmessZP);
    	var kluch;
    	nowCount= 0;
    	var uprak2 = event.data.substring(10,52);
    	if(event.data.indexOf('zp1') + 1) { kluch = 'zp1';	}
    	if(event.data.indexOf('list1enpzp9') + 1) { kluch = 'list1enpzp9';	}
    	if(event.data.indexOf('list1passportzp9') + 1) { kluch = 'list1passportzp9';	}
    	if(event.data.indexOf('list1snilszp9') + 1) { kluch = 'list1snilszp9';	}
    	

    	// говорим что сообщение отработало
    	// прячем окошко статус сообщения
        setTimeout ("$('ul#login-dp').slideUp(2000);", 0);
        // получаем таблицЫ в качестве объектОВ
    	var hotInstan2 = $('#list2onsc').handsontable('getInstance');
    	var hotInstan3 = $('#list3onsc').handsontable('getInstance');
    	// зачищаем веб таблицы (2 и 3 листы)
    	hotInstan2.clear();
    	hotInstan3.clear();
    	
    	//	блок spinner'а (включение)
      	 $("#dim2").css("height", $(document).height());
   		 $("#dim2").fadeIn();
   	     spinner.spin($('#spinner_center')[0]);
   	      ajax_cnt++;   	 	
        
   	
   	  // $('.nav-pills li:eq(2) a').tab('show');
	   	var myData2 = {uprak2, datauprmessZP, kluch}
	   	 var jqxhr = $.getJSON( "ImportZP1fromXMLToHandsontable",myData2, function(er)
	   	 {
	   		 
	   		hotInstan3.loadData(er.data1zp1ajax);
	   	 })
	   	 
	   	 .done(function(ev)
	   	 {
	   		//$('.nav-pills li:eq(1) a').tab('show');
	   		 // вставляем данные на второй лист с большого запроса (upmessa)
	   		 hotInstan2.loadData(ev.data2upr);
	   	 })
   	      
	  .fail( function(error) {
        console.log("error.responseJSON "+error.responseJSON);
        })
        
	 .always(function()
	 { 
		// блок spinner (выключение) 
	 		ajax_cnt--;
	 	    if (ajax_cnt <= 0) {
	 	       spinner.stop();
	 	       $('#dim2').fadeOut();
	 	       ajax_cnt = 0;
	 	 	}
		 
	 });
    }
};

/**
 * @param varrr
 * @returns {Boolean}
 */
function searchInDirectory(varrr) 
{
	console.log('send '+ varrr);
	//если флаг входа 0 (т.е. соединений нет открытых)
	 if(!nowCount)
	 {
		 console.log('sendON '+ varrr);
		 
		 ws.send(varrr);
		
		 // это условие для zp1 с подтверждением автоматического запуска эксель
		 if(varrr == 'ZP1' || varrr == 'A08P02test' || varrr == 'buttonZP9' || varrr == 'Zp1Ajax' || varrr == 'Zp1taskA8P4' || varrr == 'A08P14' || varrr == 'A03P07' || varrr == 'A08P08'){nowCount=0;} else {nowCount=1;}
		 
		 return true;
	 }
	 else
	 {
		 alert('Дождитесь окончания запроса ');
		 return false;
	 }
}

ws.onclose = function(event) {
	  if (event.wasClean)
	  {
		  alert('закрыто');
	  } else 
	  {
		// например, "убит" процесс сервера
		  $("#dim").css("height", $(document).height());
  		  $("#dim").fadeIn(); 
  		  console.log('test1 '+event);
  		console.log('test1.1 '+ws);
  		console.log('test1.2 '+JSON.stringify(ws));
	  }
	 
	};	


function closeConnect(event) {
	ws.close();
	console.log('test2 '+event);
	alert('pfrhskb cj cnjhjys ')
	
}

ws.onerror = function(error) {
	console.log("Ошибка " + error.message);
	console.log("Ошибка2 " + error);
	console.log("Ошибка2.1 " + JSON.stringify(error));
	};

/*
 * функция при загрузке страницы Index.jsp передает имя пользователя
 */
function transmitUserName(per) { user = per; ws.send('USERENTERINTOSYSTEM'+per);}


function showProgressBar(comm){
	
	ws.send("bar"+document.getElementById("username").value);
	
	setTimeout(function() {
		// выполнение задания
		document.getElementById(comm).submit();

		// инициализация счетчика
	    var count = 0;
	    // показываем прогресс бар
	    var bar = document.getElementById("bar");
	    var progress_panel = document.getElementById("progress");
	    
	    var oneTimeTask = document.getElementById("oneTimeTask").value;
	    
	    var taskNumber = document.getElementById("taskNumber").value;
	    
	    progress_panel.style.display = "block";
	    (function () {
	       if (count >= 400) {
	       } else {
	        // увеличиваем на 1 счетчик и изменяем прогресс бар
			count += 400/taskNumber;
	        bar.style.width = count + "px";
	        // следующая итерация цикла
	        setTimeout(arguments.callee, oneTimeTask);
	       }
	    })(); 
	}, 500) ;


	
}
	
	
	
