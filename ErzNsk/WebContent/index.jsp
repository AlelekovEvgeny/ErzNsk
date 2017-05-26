<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!-- 
		Условие при нажатии на zp1
		1) Если окно Таблица открыто 
			1.1 Логика программы: Нажимается кнопка zp1 и обновляется эксель со страницы на сайте
		2) Если окно Таблина открыто и нажимается кнопка свернуть. Это значит что пользователь открыл окно и мог что-то ввести и свернул его
			2.1 Логика программы: При нажатии кнопки Таблица и статусе visible обновляем эксель со страницы на сайте
		3) Окно закрыто. Т.е. пользователь не предпринимал ни каких действий.
			3.1 Логика программы: Сохранения в эксель не происходит
		4) Когда запрос пришел и загрузился к эксель	
			4.1 
 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Enumeration"%>
<%@ page import="java.util.Date"%>
<% 
Enumeration sesNames = session.getAttributeNames();
while (sesNames.hasMoreElements())
{
   String name = sesNames.nextElement().toString();
   Object value = session.getAttribute(name);
   
}
String user = (String)session.getAttribute("username");
String usercomp = (String)session.getAttribute("usercomp");
System.out.println("вошло на index "+user+" "+usercomp);
String ses = session.getId();
String ip = (String)session.getAttribute("ip");
String createTime =""+new Date(session.getCreationTime());
%>

<html>
<head>
<title>Главная</title>
<link rel="stylesheet" href="css/styles.css" type="text/css" />

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.css" rel="stylesheet">
<link href="assets/css/bootstrap-tagsinput.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
<link rel="stylesheet" href="css/font-awesome-animation.css">
<link href="css/handsontable.full.css" rel="stylesheet">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="js/jquery.form.js"></script>
<script src="js/bootstrap.js"></script>	
<script src="assets/script/bootstrap-tagsinput.js"></script>
<script src="js/spin.js"></script>
<script src="script/webSocketAnswer.js"></script>
<script src="js/handsontable.full.js"></script>
<script src="js/numeral.js"></script>
<script src="js/moment.js"></script>
<script src="js/fileUploadScript.js" ></script>






<script type="text/javascript">
<!--Скрипт анимированного появления страницы -->	
$(document).ready(function() 
{
	// отключаем кнопку 'несформированный запрос'
	$('#zaprosWebExcel').prop('disabled', true);
	$('#zaprosWebExcelCancel').prop('disabled', true);		
	//setTimeout ("$('textarea#textareamess').animate({opacity: 0}, 0 );", 0);
	setTimeout ("$('body').animate({opacity: 0.0}, 0 );", 0);
	setTimeout ("$('body').animate({opacity: 1}, 3000 );", 10);
	setTimeout ("$('#updateonsite').modal('show');", 2000);
	$('#zaprosWebExcel').prop('value', '');

	

});
</script>
<script type="text/javascript">

$(document).ready(function()
{ 
		
	   $('#su').click(function(event)
               {  
                   $('#texthelpqueryfiod').empty();
	                $.get('HelpQueryFiod',function(responseJson)
	                { 
		              var $ul = $('<b>').appendTo($('#texthelpqueryfiod'));
		                $.each(responseJson, function(index,item)
				        { 
		                	$('<li>').text(item).appendTo($ul);               
		                });	
	                });
               });
	   
	   $('#help_import_uprak2').click(function(event)
               {  
                   $('#text_help_import_uprak2').empty();
	                $.get('Help_Import_Uprak2',function(responseJson)
	                { 
		              var $ul = $('<b>').appendTo($('#text_help_import_uprak2'));
		                $.each(responseJson, function(index,item)
				        { 
		                	$('<li>').text(item).appendTo($ul);               
		                });	
	                });
               });
	   
               $('#refresh_tab_errorgz').click(function(event)
            		   {  

		            	    if( !ajax_cnt) // if 0
			       	 		{
			       	 			$("#dim2").css("height", $(document).height());
			       	     		 $("#dim2").fadeIn();
			       	     		 spinner.spin($('#spinner_center')[0]);
			       	        	 ajax_cnt++;
			       	 		}
            	   
        	                $.get('refresh_tab_errorgz',function(responseJson)
        	                { 
        	                	if(ajax_cnt) {	$('#dim2').fadeOut();spinner.stop();ajax_cnt = 0; }        		              
        	                })
        	                .error(function(msg) {if(ajax_cnt) {	$('#dim2').fadeOut();spinner.stop();ajax_cnt = 0; } alert(' Произошла ошибка загрузки. Обновитесь и повторите.');});
                       });
               
               
           $('#su1').click(function(event)
                   {  
                       $('#texthelpqueryoutenp').empty();
    	                $.get('HelpQueryOutEnp',function(responseJson)
    	                { 
    		              var $ul = $('<b>').appendTo($('#texthelpqueryoutenp'));
    		                $.each(responseJson, function(index,item)
    				        { 
    		                	$('<li>').text(item).appendTo($ul);               
    		                });	
    	                });
                   });
           
           
           $('#su2').click(function(event)
                   {  
                       $('#texthelpquerydivision').empty();
    	                $.get('HelpEditOutEnp',function(responseJson)
    	                { 
    		              var $ul = $('<b>').appendTo($('#texthelpquerydivision'));
    		                $.each(responseJson, function(index,item)
    				        { 
    		                	$('<li>').text(item).appendTo($ul);               
    		                });	
    	                });
                   });

	
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



    
	        var gouser = "user="+ '<%=user%>';

	           // определяем ширину загружаемого окна и встраиваем таблицу под эту ширину
	           var widthGet = $('.panel#tableexcel').width();
	           var heightGet = $('.panel#tableexcel').height();
	
	           $('div#list1onscstyle').width(widthGet-44);
	           $('div#list1onscstyle').height(heightGet+200);
	
	           $('div#list2onscstyle').width(widthGet-44);
	           $('div#list2onscstyle').height(heightGet+200);
	
	
	           $('div#list3onscstyle').width(widthGet-44);
	           $('div#list3onscstyle').height(heightGet+200);
	           // конец  			
	           
	       
				// первый лист				
	    	   var data = [[ 'ENP',' ',' ','USER_ENP','PERSON_SURNAME','PERSON_KINDFIRSTNAME','PERSON_KINDLASTNAME', 'SMO','D_12','D_13','OKATO','TYPE_POL','POL','	 	','	 ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ']
	    	             //  [ ' ', ' ',' ' ,' '],
	    	             //  [ ' ', ' ',' ' ,' ']
	    	   ];

	    	            // var container = document.getElementById('list1onsc');
	    	             // hot1 = new Handsontable(container, {
	    	              $('#list1onsc').handsontable({
	    	               data: data,
	    	               minSpareRows: 1,
	    	               rowHeaders: true,
	    	               colHeaders: true,
	    	               contextMenu: true,
	    	               manualColumnResize: true,
	    	               manualRowResize: true,
	    	             //  colWidths: [150,	40,	40,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150]
	    	             });
	    	   
				// второй лист				
	    	   var data2 = [[ 'PERSON_SERDOC','PERSON_NUMDOC','PERSON_DOCPERSONID','PERSON_SURNAME','PERSON_KINDFIRSTNAME','PERSON_KINDLASTNAME','PERSON_BIRTHDAY','PERSON_SEX	PERSON_LINKSMOESTABLISHMENTID','ENP	PERSON_ADDRESSID','PERSON_DATEINPUT','SNILS','BORN','DATEPASSPORT','ENP_PA','VS_NUM','VS_DATE','ZAD','D2','SMO','D_12','D_13','OKATO_3','TYPE_POL','POL','ENP_1','ENP_2','P14CX1','P14CX5','P14CX6','P14CX7','XPN1','XPN2','XPN3','USERNAME','ZADMINUS1','ZADPLUS40','NBLANC','VS_DATEPLUS1','USER_ENP','USER_PERSON_SURNAME','USER_PERSON_KINDFIRSTNAME','USER_PERSON_KINDLASTNAME','USER_SMO','USER_D_12','USER_D_13','USER_OKATO_3','USER_TYPE_POL','USER_POL','NVL((SELECTALFA3FROMDEVELOPER.OKSMOWHEREO.KOD=RUSSIANANDROWNUM=1),RUS)','D_V','D_SER','D_NUM','PR_FAM','PR_IM','PR_OT','LAST_FAM','LAST_IM','LAST_OT','LAST_DR	PFR_SNILS','PFR_ID','PFR_NOTID','USER_SERDOC','USER_NUMDOC','USER_DOCID','USER_DOC_DATE','D_12_PLUS1'],
	    	               [ ' ', ' ',' ' ,' '],
	       	               [ ' ', ' ',' ' ,' '],
	       	               [ ' ', ' ',' ' ,' '],
	    	               [ ' ', ' ',' ' ,' '],
	    	               [ ' ', ' ',' ' ,' '],
	       	               [ ' ', ' ',' ' ,' '],
	       	               [ ' ', ' ',' ' ,' '],
	    	               [ ' ', ' ',' ' ,' ']
	    	   ];
	    	            $('#list2onsc').handsontable({
	    	               data: data2,
	    	               minSpareRows: 1,
	    	               rowHeaders: true,
	    	               colHeaders: true,
	    	               contextMenu: true,
	    	               manualColumnResize: true,
	    	               manualRowResize: true,
	    	             //  colWidths: [150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150]
	    	             });

	    	    // Третий лист 
	    	             
  	              var data3 = [['ENP','PERSON_SURNAME','PERSON_KINDFIRSTNAME','PERSON_KINDLASTNAME','TRUNC(P.PERSON_BIRTHDAY)','GD','ENP_1','ENP_2','OKATO_2','SMO','D_12','D_13','OKATO_3','TYPE_POL','POL','QRI_1','QRI_2','QRI_3','QRI_4','NPP','D_INPUT','PID7','PID8','PID29'],
   	             
	               [ ' ', ' ',' ' ,' '],
	               [ ' ', ' ',' ' ,' '],
	               [ ' ', ' ',' ' ,' '],
	               [ ' ', ' ',' ' ,' '],
   	               [ ' ', ' ',' ' ,' ']
	    	   ];
   	              $('#list3onsc').handsontable({
   	               data: data3,
   	               minSpareRows: 1,
   	               rowHeaders: true,
   	               colHeaders: true,
   	               contextMenu: true,
   	               manualColumnResize: true,
   	               manualRowResize: true,
   	             //  colWidths: [150, 100, 100, 100, 150, 230, 150,150,30,130,80,150,50,50,150,30,30,30,30,50,100,100,150]
   	             });
   	              
   	           
   	             /*
   	               Кнопка Экспорт в окне таблица
   	             */

   	          $('#btnexportfromhandsontableTOExcel').click(function()
    	          {
	   	        	 var hot1 = $('#list1onsc').handsontable('getInstance');
	            	 var hot2 = $('#list2onsc').handsontable('getInstance');
	            	 var hot3 = $('#list3onsc').handsontable('getInstance');
		     	        if ($('.panel#tableexcel').is(':visible'))
		         	    {
		     	            	// вычисляем размеры массива в этой таблице вставляем его в эксель
		     	            	
			    	            	 		var e1 =hot1.countRows()-hot1.countEmptyRows(true);
			    	            	 		var wd1 = hot1.countCols()-hot1.countEmptyCols(true);
			    	            	 		var e2 =hot2.countRows()-hot2.countEmptyRows(true);
			    	            	 		var wd2 = hot2.countCols()-hot2.countEmptyCols(true);
			    	            	 		var e3 =hot3.countRows()-hot3.countEmptyRows(true);
			    	            	 		var wd3 = hot3.countCols()-hot3.countEmptyCols(true);
			    	            	 		console.log('list1row '+e1+' list1col '+ wd1);
			    	            	 		
			    	            	 		if( !ajax_cnt) // if 0
			    	            	 		{
			    	            	 			$("#dim2").css("height", $(document).height());
			        	        	     		 $("#dim2").fadeIn();
			        	        	     		 spinner.spin($('#spinner_center')[0]);
			        	        	        	 ajax_cnt++;
			    	            	 		}
		        	        	        	 
		        	        	        		if ($('#zaprosWebExcel').attr("value") != '')
		        	        	        		{
		        	        	        			var buttonQuery = "button="+ $('#zaprosWebExcel').attr("value");
		        	        	        			var myData = { list1:hot1.getData(0,0,e1-1,wd1-1), list2:hot2.getData(0,0,e2-1,wd2-1),list3:hot3.getData(0,0,e3-1,wd3-1),gouser, buttonQuery }		
		        	        	        		}
		        	        	        		else
		        	        	        		{
		        	        	        			var myData = { list1:hot1.getData(0,0,e1-1,wd1-1), list2:hot2.getData(0,0,e2-1,wd2-1),list3:hot3.getData(0,0,e3-1,wd3-1),gouser  }		
		        	        	        		}
			    	            	 		 
			    	      	                $.post('ExportToExcelFromEmdedTable',JSON.stringify(myData),function(res)
			    	      	                {
			    	      	                	if(ajax_cnt) {	$('#dim2').fadeOut();spinner.stop();ajax_cnt = 0; }
			    	      	                	console.log('test'+ajax_cnt);
			        	      	            },'json');
			        	      	          	//.error(function(msg) {if(ajax_cnt) {	$('#dim2').fadeOut();spinner.stop();ajax_cnt = 0; } });
			      	            
		     	        }
	              });
   	             
   					   
	   				   var uprak2='';	
		               var datauprmessZP='';
		               var kluch='';
		               
			   	      
   	       			$('#activateGetJson2').click(function()
	    	          {
   	       				$('#importuprak2_mod').modal('hide');
   	       			 	
	   	       			$("#dim2").css("height", $(document).height());
	    	     		$("#dim2").fadeIn();
	    	     		spinner.spin($('#spinner_center')[0]);
	    	        	 ajax_cnt++;
   	       				
   	       				$('#upload-form_uprak2').ajaxForm({
		               	success: function(data) 
		               	{
			              	uprak2 =  data.data1;	
			              	datauprmessZP = data.data2;
			              	kluch = data.data3;
			              	
			              	
			              	  	
		               	},
               		 	complete:function(data)
               		 	{
               		 		uprak2_after();
               		 		$('#dim2').fadeOut();spinner.stop();ajax_cnt = 0;
		               	},
	               			error: function(msg) {alert("Произошла ошибка загрузки. Обновите и повторите заново либо обратитесь к администратору");}
	           			});
   	       			  
	    	        });
   	       			
   	       		$('input').on('itemAdded', function(event) {
   	       	  		
		   	    		let dataZP3 = $('#zp3filename').val();
		   	    		
		   	        	
		   	        	//блок spinner'а (включение)
		   	         	$("#dim2").css("height", $(document).height());
		   	      		$("#dim2").fadeIn();
		   	      	    spinner.spin($('#spinner_center')[0]);
		   	      	    ajax_cnt++;
		   	      	    
		   	  	     let myData2 = {dataZP3};
		   	  		 let hotInstan1 = $('#list1onsc').handsontable('getInstance');
		   			 var jqxhr = $.getJSON( "ImportZP3fromXMLToHandsontable",myData2, function(er)
		   			 {
		   					$('#importuprak2zp3_mod').modal('hide');
		    	        	
		       	        	hotInstan1.clear();
		   			 })
		   			 .done(function(er)
		   			 {
		   				hotInstan1.loadData(er.data2upr);
		   			 })
		   			 .fail( function(error) { console.log("error.responseJSON "+error.responseJSON); })
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
   	       			
   	       			//end
   	       		});
   	       			
   	       		
   	       			
   	       			/*
   	       				на вход подем имена упрака2 при импорте в ручную 
   	       			*/
   	       				function uprak2_after(){
   	       				uprak2=''+ uprak2;
   	       				kluch=''+kluch;
   	       				datauprmessZP=''+datauprmessZP;
								 var hotInstan2 = $('#list2onsc').handsontable('getInstance');
				            	 var hotInstan3 = $('#list3onsc').handsontable('getInstance');
				            	 
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
				               });
   	       			}	
   	             
   	       				$('#activateGetJson').click(function()
     	          		{
   	       				$('#importFromExcel').modal('hide');
		            	   $("#dim2").css("height", $(document).height());
		       	     	   $("#dim2").fadeIn();
		       	     	   spinner.spin($('#spinner_center')[0]);
		       	           ajax_cnt++;
		       	           
			   	        	$('#upload-form').ajaxForm({
				               	success: function(data) 
				               	{
				            	   
				               		var hot1 = $('#list1onsc').handsontable('getInstance');
				            	  	var hot2 = $('#list2onsc').handsontable('getInstance');
				             	 	var hot3 = $('#list3onsc').handsontable('getInstance');     
	
					              	hot1.loadData(data.data1);
					        	    hot2.loadData(data.data2);
					        		hot3.loadData(data.data3);
					        		
					        		if(data.kluch != undefined)
					        		{ 
					        			// включаем кнопку сформированного запроса
        	            	 			$('#zaprosWebExcel').prop('disabled', false);
        	            	 			// включаем кнопку расформировать запрос
        	            	 			$('#zaprosWebExcelCancel').prop('disabled', false);
        	            	 			// обновляем кнопку: имя запроса 
        	            	 			$('#zaprosWebExcel').text('Отправить '+ data.kluch);
        	            	 			// устанавливаем value кнопке запрос несформирован или сформирован
        	            	 			//$('#zaprosWebExcel').prop('value', data.kluch);
        	            	 			$('#zaprosWebExcel').val(data.kluch.toString());
        	            	 			console.log('qqq1 '+$('#zaprosWebExcel').val());
        	            	 			console.log('qqq2 '+data.kluch);
        	            	 			//evenClickzaprosWebExcel(data.kluch);
				        			}
					        		
				               	},
		               		 
			               			error: function(msg) {console.log("Couldn't upload file");}
			           		});

			   	        	$('#dim2').fadeOut();spinner.stop();ajax_cnt = 0;
     	          		});	
						   	          			
  	          		
   	             
   	             /*
                    вся та беда подгружается потом hide 
                    */
   	             $('.panel#tableexcel').animate({ 'height': 'hide'}, 0);  
	    	             
						 /*
						 	Кнопка Таблица
						 	При нажатии заполняем таблицу на странице С ЭКСЕЛЯ
						 */	
	    	             $('#onofftable').click(function (event)
	    	    	              {
				    	             // ЕСЛИ ОТКРЫТО ОКНО .СТАТУС ОБНОВЛЕНИЯ. ТО ВЫДАЕМ СООБЩЕНИЕ ЧТО ТАБЛИЦЕЙ ПОЛЬЗОВАТЬСЯ НЕЛЬЗЯ
			    	            	 if ($('ul#login-dp').is(':visible')) { $('#warningwebtable').modal('show'); }
			    	            	 else
				    	             {
			    	            		 
					    	            	 var hot1 = $('#list1onsc').handsontable('getInstance');
					     	            	 var hot2 = $('#list2onsc').handsontable('getInstance');
					     	            	 var hot3 = $('#list3onsc').handsontable('getInstance');
						    	          	  
					    	     	        if ($('.panel#tableexcel').is(':visible')) {
					    	     	                $('.panel#tableexcel').animate({ 'height': 'hide' }, 2000);
				
					    	     	            	// при клике на кнопку таблица - если вебэксель открыт то прячем его и вычислив размеры массива в этой таблице вставляем его в эксель
					    	     	            	/*  ЗАКОМЕНТАРИЛ ИМПОРТ В ЭКСЕЛЬ
					    		    	            	 		var e1 =hot1.countRows()-hot1.countEmptyRows(true);
					    		    	            	 		var wd1 = hot1.countCols()-hot1.countEmptyCols(true);
					    		    	            	 		var e2 =hot2.countRows()-hot2.countEmptyRows(true);
					    		    	            	 		var wd2 = hot2.countCols()-hot2.countEmptyCols(true);
					    		    	            	 		var e3 =hot3.countRows()-hot3.countEmptyRows(true);
					    		    	            	 		var wd3 = hot3.countCols()-hot3.countEmptyCols(true);
					    		    	            	 		console.log('list1row '+e1+' list1col '+ wd1);
					    		    	            	 		
					    		    	            	 		var myData = { list1:hot1.getData(0,0,e1-1,wd1-1), list2:hot2.getData(0,0,e2-1,wd2-1),list3:hot3.getData(0,0,e3-1,wd3-1),gouser  } 
					    		    	      	                $.post('ExportToExcelFromEmdedTable',JSON.stringify(myData),function(res)
					    		    	      	                {
					    		    	      	                
					    		        	      	            },'json');
		    		        	      	            */
					    	     	        } else
					    	         	    {	$('.panel#tableexcel').animate({ 'height': 'show'}, 2000);
					    	         	    	/* ЗАКОММЕНТАРИЛ ЭКСПОРТ ЭКСЕЛЬ
						    	         	    var jqxhr = $.getJSON( "ImportFromExcelToHandsontable",gouser, function(data)
						    	    	        {
						    	         		  hot1.loadData(data.data1);
						    	         		  hot2.loadData(data.data2);
						    	         		  hot3.loadData(data.data3);
							            	    })
						            	    	*/
					    	         	    };
			    	             	 }	    
	    	            		  });
	    	             /*Изменяем вебТаблицу  при нажати п02 в запросы ффмос прогружаем данные
	    	             КНОПКА П02
	        	             */  
	        	             var id;
	        	             var datatargetm;
	        	             $('#A08P02today,#A08P02howINsmo,#A08P02howINsmoPID29,#buttonA08P02,#A08P02akaZP3').click(function (idtoplevel)
			        	     {	
	        	            	 // ловим id нажатой кнопки
    	            	 		 var ids = $(this).attr("id");
    	            	 		 // если кнопка первого уровня то ( с нажатой кнопки  с меню запросы ффомс)
    	            	 		 if('buttonA08P02' === ids) {	id = ids; datatargetm = $(this).attr("data-target");  console.log('jkj '+id+'  '+ datatargetm);	}
    	            	 		 
    	            	 		//если кнопка второго уровня то 		 
 	        	            	if( 'A08P02today' === ids || 'A08P02howINsmo' === ids || 'A08P02howINsmoPID29' === ids || 'A08P02akaZP3' == ids)
 		        	            {
 	        	            		var flag = 0;
		        	            	 $(""+datatargetm).modal('hide');
		        	 				 $('#modalfoms').modal('hide');
		        	 				 //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
       	            	 		     if ($('.panel#tableexcel').is(':visible')) 
	        	            	 	{
       	            	 		    	var hotInstance	 = $('#list1onsc').handsontable('getInstance');
        	            	 			var hotInstance2 = $('#list2onsc').handsontable('getInstance');
        	            	 			var hotInstance3 = $('#list3onsc').handsontable('getInstance'); 
        	            	 			
        	            	 			// определяем количество строк
        	            	 			var e1 =hotInstance.countRows()-hotInstance.countEmptyRows(true);
        	            	 			var wd1 = hotInstance.countCols()-hotInstance.countEmptyCols(true);
        	            	 			
        	            	 			var e2 =hotInstance2.countRows()-hotInstance2.countEmptyRows(true);
        	            	 			var wd2 = hotInstance2.countCols()-hotInstance2.countEmptyCols(true);
        	            	 			
        	            	 			var e3 =hotInstance3.countRows()-hotInstance3.countEmptyRows(true);
        	            	 			var wd3 = hotInstance3.countCols()-hotInstance3.countEmptyCols(true);
        	            	 			
        	            	 			 if('A08P02today' === ids){ var gouser = "zapros=A08P02today";	var myDatap02 = { list1:hotInstance.getData(0,0,e1-1,wd1-1), list2:hotInstance2.getData(0,0,e2-1,wd2-1), list3:hotInstance3.getData(0,0,e3-1,wd3-1),gouser	}	}
 		        				         if('A08P02howINsmo' === ids){ var gouser = "zapros=A08P02howINsmo";	var myDatap02 = { list1:hotInstance.getData(0,0,e1-1,wd1-1), list2:hotInstance2.getData(0,0,e2-1,wd2-1), list3:hotInstance3.getData(0,0,e3-1,wd3-1), gouser	} }
 		        				         if('A08P02howINsmoPID29' === ids){ var gouser = "zapros=A08P02howINsmoPID29";	var myDatap02 = { list1:hotInstance.getData(0,0,e1-1,wd1-1), list2:hotInstance2.getData(0,0,e2-1,wd2-1), list3:hotInstance3.getData(0,0,e3-1,wd3-1), gouser	} }
 		        				         if('A08P02akaZP3' === ids){ var gouser = "zapros=A08P02akaZP3";	var myDatap02 = { list1:hotInstance.getData(0,0,e1-1,wd1-1), list2:hotInstance2.getData(0,0,e2-1,wd2-1), list3:hotInstance3.getData(0,0,e3-1,wd3-1), gouser	} }
 		        				          
 		        				         
 		        				        $("#dim2").css("height", $(document).height());
	        	        	     		$("#dim2").fadeIn();
	        	        	     		spinner.spin($('#spinner_center')[0]);
	        	        	        	 ajax_cnt++;
        	        	        	    
	        	        	        	 $.ajax({
		        						        url: "ExportFromWebtab",
		        						        type: 'POST',
		        						        dataType: 'json',
		        						        data: JSON.stringify(myDatap02),
		        						        contentType: 'application/json',
		        						        success: function (res)
		        						        {
	        	        	        		 		hotInstance.loadData(res.list1);
	        	        	        		 		
	        	        	        		 		$('#btnexportfromhandsontableTOExcel').trigger('click');
	 		        				        	 	$('#dim2').fadeOut();spinner.stop();ajax_cnt = 0;
	 		        				        		if(res.info > 0){console.log('info '+res.info); setTimeout ("$('#warning1').modal('show');", 2000);}
	 		        				        		// берем значение с нажатой кнопки  с меню запросы ффомс (кнопка верхнего уровня)
			        	            	 			var val = $('#'+id).attr("value");
			        	            	 			// включаем кнопку сформированного запроса
			        	            	 			$('#zaprosWebExcel').prop('disabled', false);
			        	            	 			// включаем кнопку расформировать запрос
			        	            	 			$('#zaprosWebExcelCancel').prop('disabled', false);
			        	            	 			// обновляем кнопку: имя запроса 
			        	            	 			$('#zaprosWebExcel').text('Отправить '+val);
			        	            	 			// устанавливаем value кнопке запрос несформирован или сформирован
			        	            	 			$('#zaprosWebExcel').prop('value', val);
			        						    },
			        						    error: function (jqXHR, textStatus, errorThrown){
			        						    	$('#dim2').fadeOut();spinner.stop();ajax_cnt = 0; console.log(' Произошла ошибка загрузки. Обновитесь и повторите.'+JSON.stringify(jqXHR));
			        						    	console.log(' textStatus '+JSON.stringify(textStatus));
			        						    	console.log(' errorThrown '+JSON.stringify(errorThrown));
			        						    }
		        						        
		        						 });
	        	            	 	}
 		        	            }
			        	     });	
	        	             
	        	             /*Функция обрабатывает нажатие клавиши сформированного запроса в окне таблица
		        	 			*/
		        	 				    $('#zaprosWebExcel').click(function ()
		        	 					{
		        	 				    	var vl = $('#zaprosWebExcel').attr("value");
		        	 				    	// обновляем данные из таблицы
			        	 				  var inst1Lista = $('#list1onsc').handsontable('getInstance');	
		        					      // передаем в вебсокет кнопку нажатую в запросе фомс (кнопка вида дапроса)
		        				          searchInDirectory(vl);
		        				       	  // вычисляем количество строк
			        	 			      var e1 =inst1Lista.countRows()-inst1Lista.countEmptyRows(true);
			        	 				  var wd1 = inst1Lista.countCols()-inst1Lista.countEmptyCols(true);
			        	 				  // если ячейка содержит "button=" то эту строку вычеркиваем из списка пайщиков конссесионеров
			        	 				  var kj = inst1Lista.getData(e1-1,0,e1-1,0)+'';
										  if(kj.indexOf('button=')>=0)
										  {
											  var myData = { list1:inst1Lista.getData(1,3,e1-2,wd1-1)}
										  }
										  else
										  {
	        				          		var myData = { list1:inst1Lista.getData(1,3,e1-1,wd1-1)}
										  }
		        				          var r = JSON.stringify(myData);
		        				          // передаем в вебсокет даные с первого листа
		        				          searchInDirectory(r);
		        				    	  setTimeout ("$('ul#login-dp').slideDown(2000);", 1000);  	
		        				    	  $('#tamessage').val("> Послан запрос "+vl+"...");
		        				    	  //затираем после отправки
			        	 					/* for(var i=1; i<e1;i++)
				        	            	 		{
					        	 						for(var j=1; j<wd1;j++)
					        	            	 		{
					        	 							inst1Lista.setDataAtCell(i,j,'');
						        	            	 	}
					        	            	 	}
			        	 					*/
		        	 				    	// после нажатия делаем обратно кнопку КАК при несформированном запросе
		        	 				    	// включаем кнопку сформированного запроса
	        	            	 			$('#zaprosWebExcel').prop('disabled', true);
	        	            	 			$('#zaprosWebExcelCancel').prop('disabled', true);
	        	            	 			// обновляем кнопку: имя запроса 
	        	            	 			$('#zaprosWebExcel').text('Запрос не сформирован');
	        	            	 			/*блок передачи json с таблицы
	        	            	 			*/
		        	 					});
		        	 			 	

	    	             /*Изменяем вебТаблицу  при нажати п02 в запросы ффмос прогружаем данные
	    	             КНОПКА П02
	        	               
	        	             var id;
	        	             var datatargetm;
	        	             $('#A08P02today,#A08P02howINsmo,#buttonA08P02').click(function (idtoplevel)
			        	     {
				        	             
				        	             // ловим id нажатой кнопки
	        	            	 		 var ids = $(this).attr("id");
	        	            	 		 // если кнопка первого уровня то ( с нажатой кнопки  с меню запросы ффомс)
	        	            	 		 if('buttonA08P02' === ids) {	id = ids; datatargetm = $(this).attr("data-target");  console.log('jkj '+id+'  '+ datatargetm);	}
	        	            	//если кнопка второго уровня то 		 
	        	            	if( 'A08P02today' === ids || 'A08P02howINsmo' === ids)
		        	            {		
			        	            	var flag = 0;
			        	            	 $(""+datatargetm).modal('hide');
			        	 				 $('#modalfoms').modal('hide');
			        	 				 
	        	            	 		if ($('.panel#tableexcel').is(':visible')) 
		        	            	 	{
	        	            	 			var hotInstance	 = $('#list1onsc').handsontable('getInstance');
	        	            	 			var hotInstance2 = $('#list2onsc').handsontable('getInstance');
	        	            	 			var hotInstance3 = $('#list3onsc').handsontable('getInstance');
	        	            	 			var date = new Date();
	        	            	 			var day = date.getDate();
	        	            	 			var mouth = date.getMonth()+1;
	        	            	 			var year =  date.getFullYear();
	        	            	 			var aktDate = year+'-'+mouth+'-'+day;
	        	            	 			
	        	            	 			// определяем количество строк
	        	            	 			var countR1 =hotInstance.countRows()-hotInstance.countEmptyRows(true);
	        	            	 			var countR2 =hotInstance2.countRows()-hotInstance2.countEmptyRows(true);
	        	            	 			var countR3 =hotInstance3.countRows()-hotInstance3.countEmptyRows(true);
	        	            	 		   
		        	            			for(var i=1; i<countR1;i++)
			        	            	 	{
		        	            			    for(var j=1; j<countR3;j++)
			        	            	 		{
		        	            					if(	parseInt(hotInstance.getDataAtCell(i,0)) == parseInt(hotInstance3.getDataAtCell(j,0)) && parseInt(hotInstance3.getDataAtCell(j,19))== parseInt("0") && parseInt(hotInstance3.getDataAtCell(j,12))== parseInt("50000") && hotInstance3.getDataAtCell(j,11)==="")

		        	            					{
														console.log('Отработала');
		        	            						
			        	            	 		    	hotInstance.setDataAtCell(i,7,hotInstance3.getDataAtCell(j,9)); // смо
			        	            	 		    	hotInstance.setDataAtCell(i,8,hotInstance3.getDataAtCell(j,10)); // д_12
			        	            	 		    	hotInstance.setDataAtCell(i,10,hotInstance3.getDataAtCell(j,12)); // окато
			        	            	 		    	hotInstance.setDataAtCell(i,11,hotInstance3.getDataAtCell(j,13)); // тип полиса
			        	            	 		    	hotInstance.setDataAtCell(i,12,hotInstance3.getDataAtCell(j,14)); // № полиса
			        	            	 		    	hotInstance.setDataAtCell(0,24,'ДР'); // пол 
			        	            	 		    	hotInstance.setDataAtCell(i,24,hotInstance3.getDataAtCell(j,21)); // ДР
			        	            	 		    	hotInstance.setDataAtCell(0,19,'ПОЛ'); // пол 
			        	            	 		    	hotInstance.setDataAtCell(i,19,hotInstance3.getDataAtCell(j,22)); // пол
				        	            	 		
			        	            	 				
			        	            	 		    	for(var k=1; k<countR2;k++)
					        	            	 		{	// если совпадают енп внутренние на первом и втором листах
						        	            	 		if(parseInt(hotInstance.getDataAtCell(i,0)) == parseInt(hotInstance2.getDataAtCell(k,9)))   // сравниваем по внутр енп
							        	            	 	{     
							        	            	 		//если нажата кнопка...
							        	            	 		if(	'A08P02today' === ids )  { hotInstance.setDataAtCell(i,9,aktDate);     }
							        	            	 		if(	'A08P02howINsmo' === ids	)  {  hotInstance.setDataAtCell(i,9,hotInstance2.getDataAtCell(k,11));   }
						        	            	 			//если нажата кнопка...
						        	            	 			if(hotInstance2.getDataAtCell(k, 15) === '') { hotInstance.setDataAtCell(i,3,hotInstance2.getDataAtCell(k,9)); }
						        	            	 			else{ hotInstance.setDataAtCell(i,3,hotInstance2.getDataAtCell(k,15));}
						        	            	 			hotInstance.setDataAtCell(i,4,hotInstance2.getDataAtCell(k,3));	// фамилия
					        	            	 		    	hotInstance.setDataAtCell(i,5,hotInstance2.getDataAtCell(k,4));	//  имя
					        	            	 		    	hotInstance.setDataAtCell(i,6,hotInstance2.getDataAtCell(k,5));	// отчество
						        	            	 			hotInstance.setDataAtCell(i,13,""); // снилс со второй страницы
						        	            	 			hotInstance.setDataAtCell(i,16,hotInstance2.getDataAtCell(k,0)); // серия паспорта
						        	            	 			hotInstance.setDataAtCell(i,17,hotInstance2.getDataAtCell(k,1)); // номер паспорта
						        	            	 			hotInstance.setDataAtCell(0,18,'Дата выдачи'); // 
						        	            	 			hotInstance.setDataAtCell(i,18,hotInstance2.getDataAtCell(k,14)); // дата выдачи
						        	            	 			hotInstance.setDataAtCell(0,20,'Дата окончания докум (D2)'); // 
						        	            	 			hotInstance.setDataAtCell(i,20,hotInstance2.getDataAtCell(k,19)); // дата окончания документа удл
						        	            	 			hotInstance.setDataAtCell(0,21,'Место рождения'); // 
						        	            	 			hotInstance.setDataAtCell(i,21,hotInstance2.getDataAtCell(k,13)); // место рождения
						        	            	 			hotInstance.setDataAtCell(0,22,'Гражданство'); // 
						        	            	 			hotInstance.setDataAtCell(i,22,hotInstance2.getDataAtCell(k,50)); //гражданство
						        	            	 			hotInstance.setDataAtCell(0,23,'Код основного документа'); // 
						        	            	 			hotInstance.setDataAtCell(i,23,hotInstance2.getDataAtCell(k,2)); // код  основного документа
							        	            	 	}
			        	            	 				}
			        	            	 			}
			        	            	 		}
		        	            			    
		        	            			    if(hotInstance.getDataAtCell(i,3) === "" || hotInstance.getDataAtCell(i,3) === null){	flag=1;	}
					        	            }
											 if(flag > 0 ){	setTimeout ("$('#warning1').modal('show');", 2000);	}
		        	            				
        		        	 			
		        	            	 		
		        	            	 			// берем значение с нажатой кнопки  с меню запросы ффомс (кнопка верхнего уровня)
		        	            	 			var val = $('#'+id).attr("value");
		        	            	 			// включаем кнопку сформированного запроса
		        	            	 			$('#zaprosWebExcel').prop('disabled', false);
		        	            	 			// включаем кнопку расформировать запрос
		        	            	 			$('#zaprosWebExcelCancel').prop('disabled', false);
		        	            	 			// обновляем кнопку: имя запроса 
		        	            	 			$('#zaprosWebExcel').text('Отправить '+val);
		        	            	 			// устанавливаем value кнопке запрос несформирован или сформирован
		        	            	 			$('#zaprosWebExcel').prop('value', val);
		        	            	 			// Функция обрабатывает нажатие клавиши сформированного запроса в окне таблица
		        	            	 			evenClickzaprosWebExcel(val);
		        	            	 			

		        	            	 			
		        	            	 	}		
     	        	        	}
			        	     });*/

			        	     $('#A08P08').click(function ()
					        	     {	
		    	            	 		
				        	    		if ($('.panel#tableexcel').is(':visible')) 
				        		 		{
				        		 			
				        					$('#modalfoms').modal('hide');
				        	       	 		//setTimeout ("$('ul#login-dp').slideDown(2000);", 1000);  	
				        					//$('#tamessage').val("> Послан запрос "+this.value+"...");
				        					// отправляем ключ что это такой-то запрос
				        					
				        					
				        					let hotInstan1 = $('#list1onsc').handsontable('getInstance');
				        					let e1 =hotInstan1.countRows()-hotInstan1.countEmptyRows(true);
				        					let wd1 = hotInstan1.countCols()-hotInstan1.countEmptyCols(true);
				        					
				        					let hotInstance2 = $('#list2onsc').handsontable('getInstance');
				        					let e2 =hotInstance2.countRows()-hotInstance2.countEmptyRows(true);
	        	            	 			let wd2 = hotInstance2.countCols()-hotInstance2.countEmptyCols(true);
	        	            	 			
				        					let hotInstance3 = $('#list3onsc').handsontable('getInstance');
				        					let e3 =hotInstance3.countRows()-hotInstance3.countEmptyRows(true);
	        	            	 			let wd3 = hotInstance3.countCols()-hotInstance3.countEmptyCols(true);
	        	            	 			
	        	            	 			let myData = { list1:hotInstan1.getData(0,0,e1-1,wd1-1), list2:hotInstance2.getData(0,0,e2-1,wd2-1), list3:hotInstance3.getData(0,0,e3-1,wd3-1)	};
				        					 		
	        	            	 			$("#dim2").css("height", $(document).height());
		        	        	     		$("#dim2").fadeIn();
		        	        	     		spinner.spin($('#spinner_center')[0]);
		        	        	        	 ajax_cnt++;
	        	        	        	    
		        	        	        	 $.ajax({
			        						        url: "process_A08P08",
			        						        type: 'POST',
			        						        dataType: 'json',
			        						        data: JSON.stringify(myData),
			        						        contentType: 'application/json',
			        						        success: function (res)
			        						        {
		        	        	        		 		hotInstan1.loadData(res.list1);
		        	        	        		 		
		        	        	        		 		$('#btnexportfromhandsontableTOExcel').trigger('click');
		 		        				        	 	$('#dim2').fadeOut();spinner.stop();ajax_cnt = 0;
		 		        				        	 	
		 		        				        		if(res.info > 0){console.log('info '+res.info); setTimeout ("$('#warning1').modal('show');", 2000);}
		 		        				        		
		 		        				        		// берем значение с нажатой кнопки  с меню запросы ффомс (кнопка верхнего уровня)
				        	            	 			var val = $('#'+id).attr("value");
				        	            	 			// включаем кнопку сформированного запроса
				        	            	 			$('#zaprosWebExcel').prop('disabled', false);
				        	            	 			// включаем кнопку расформировать запрос
				        	            	 			$('#zaprosWebExcelCancel').prop('disabled', false);
				        	            	 			// обновляем кнопку: имя запроса 
				        	            	 			$('#zaprosWebExcel').text('Отправить А08П08');
				        	            	 			// устанавливаем value кнопке запрос несформирован или сформирован
				        	            	 			$('#zaprosWebExcel').prop('value', 'A08P08');
				        						    },
				        						    error: function (jqXHR, textStatus, errorThrown){
				        						    	$('#dim2').fadeOut();spinner.stop();ajax_cnt = 0; console.log(' Произошла ошибка загрузки. Обновитесь и повторите.'+JSON.stringify(jqXHR));
				        						    	console.log(' textStatus '+JSON.stringify(textStatus));
				        						    	console.log(' errorThrown '+JSON.stringify(errorThrown));
				        						    }
			        						        
			        						 });
	        	            	 			
				        		 		}
					        	     });
			        	     
			        	     
			        	     
			        	     
	        	             /*
		    	             	КНОПКА ZP9
	        	             */  
	        	             	var idZP9;
	        	             	var datatargetmZP9;
		        	             $('#ZP9enp,#ZP9passport,#buttonZP9,#ZP9snils').click(function ()
				        	     {
		        	            	 
		        	            		// ловим id нажатой кнопки
	        	            	 		var ids = $(this).attr("id");
	        	            	 		// если кнопка первого уровня то ( с нажатой кнопки  с меню запросы ффомс)
	        	            	 		 if('buttonZP9' === ids) {	idZP9 = ids; datatargetmZP9 = $(this).attr("data-target");  console.log('Catch on ZP9 '+idZP9+'  '+ datatargetmZP9);	}	 
	        	            	 		//если кнопка второго уровня то
	        	            	 		if ($('.panel#tableexcel').is(':visible')) 
		        	            	    { 		 
			     	        	            	if( 'ZP9enp' === ids || 'ZP9passport' === ids || 'ZP9snils' === ids)
			     		        	            {		
		     			        	 					// берем значение с нажатой кнопки  с меню запросы ффомс (кнопка верхнего уровня)
				        	            	 			var val = $('#'+id).attr("value");
				        	            	 			// обновляем данные из таблицы
				        	            	 			var inst1Lista = $('#list1onsc').handsontable('getInstance');
					        	            	 		// вычисляем количество строк
			      			        	 			      var e1 =inst1Lista.countRows()-inst1Lista.countEmptyRows(true);
			      			        	 				  var wd1 = inst1Lista.countCols()-inst1Lista.countEmptyCols(true);
			      		        				          if('ZP9enp' === ids){	var myData = { list1enpzp9:inst1Lista.getData(1,0,e1-1,0)}	}
			      		        				          if('ZP9passport' === ids){	var myData = { list1passportzp9:inst1Lista.getData(1,0,e1-1,0)}	}
			      		        				          if('ZP9snils' === ids){	var myData = { list1snilszp9:inst1Lista.getData(1,0,e1-1,0)}	}
			      		        				          var r = JSON.stringify(myData);
			      		        				     	  // передаем в вебсокет кнопку нажатую в запросе фомс (кнопка вида дапроса или же кнопка первого уровня или же ZP)
					        	            	 		  searchInDirectory(idZP9);
			      		        				          // передаем в вебсокет даные с первого листа
			      		        				          searchInDirectory(r);

			      		        				        $(""+datatargetmZP9).modal('hide');
			     			        	 				$('#modalfoms').modal('hide');
			     			        	 				setTimeout ("$('ul#login-dp').slideDown(2000);", 1000);  	
			     		        				    	$('#tamessage').val("> Послан запрос "+ids+"...");
			     		        	            }
		        	            	    }
			        	            	  else
					        	            {
			        	            		 $('#warningwebtable2').modal('show');    
							        	    }          
					        	 });

			        	     
							/*
								Кнопка Очистки - "Удалить из запроса"
							*/
	        	            
	        	            	 $('#delrowsA08P02').click(function ()
 	     		        	    {
	        	            		 		var hotInstance = $('#list1onsc').handsontable('getInstance');
	        	            		 		var myData ={list1:hotInstance.getData()  }
			        	            	    
			        	            	       $("#dim3").css("height", $(document).height());
		        	        	     		   $("#dim3").fadeIn();
		        	        	     		   spinner.spin($('#spinner_center2')[0]);
		        	        	        	   ajax_cnt++;
		        	        	        	   
					        	        	   $.ajax({
			        						        url: "DelEmptyRowsFromWebExcel",
			        						        type: 'POST',
			        						        dataType: 'json',
			        						        data: JSON.stringify(myData),
			        						        contentType: 'application/json',
			        						        success: function ( data) {hotInstance.loadData(data.data1);},
			        						        complete: function(jqXHR, textStatus)
			        						        {
				        	            	    	       
				        	            	    	}    
			        						    });
			        	            	    		setTimeout(checkProgress, 2000);
	 						        	        
 	     		        	 	});	
	        	     			/*
									 "Задание P03"
								*/
		        	            
		        	            	 $('#taskP03').click(function ()
	 	     		        	    {
		        	            		 if ($('.panel#tableexcel').is(':visible'))
		        	     				 {
		        	            		 		var hotInstance = $('#list1onsc').handsontable('getInstance');
		        	            		 		var myData ={list1:hotInstance.getData()  }
				        	            	    
				        	            	       $("#dim3").css("height", $(document).height());
			        	        	     		   $("#dim3").fadeIn();
			        	        	     		   spinner.spin($('#spinner_center2')[0]);
			        	        	        	   ajax_cnt++;
			        	        	        	  
						        	        	   $.ajax({
				        						        url: "TaskP03",
				        						        type: 'POST',
				        						        dataType: 'json',
				        						        data: JSON.stringify(myData),
				        						        contentType: 'application/json',
				        						        success: function ( data) {hotInstance.loadData(data.data1);$('#btnexportfromhandsontableTOExcel').trigger('click');},
				        						        complete: function(jqXHR, textStatus)  {}    
				        						    });
				        	            	    		setTimeout(checkProgress, 2000);
		        	     				 }
		        	            		 else
		        	            		 {
		        	            			 $('#warningwebtable2').modal('show');
		        	            		 }
	 	     		        	 	});	
	        	     			
		        	            	 /*
									 "Задание P02"
									 */
		        	            	 $('#taskP02').click(function ()
	 	     		        	    {
		        	            	
		        	            		 if ($('.panel#tableexcel').is(':visible'))
		        	     				 {
		        	            		 		var hotInstance = $('#list1onsc').handsontable('getInstance');
		        	            		 		var myData ={list1:hotInstance.getData()  }
				        	            	    
				        	            	       $("#dim3").css("height", $(document).height());
			        	        	     		   $("#dim3").fadeIn();
			        	        	     		   spinner.spin($('#spinner_center2')[0]);
			        	        	        	   ajax_cnt++;
			        	        	        	   
						        	        	   $.ajax({
				        						        url: "TaskP02",
				        						        type: 'POST',
				        						        dataType: 'json',
				        						        data: JSON.stringify(myData),
				        						        contentType: 'application/json',
				        						        success: function ( data) {hotInstance.loadData(data.data1);$('#btnexportfromhandsontableTOExcel').trigger('click');},
				        						        complete: function(jqXHR, textStatus)
				        						        {
					        	            	    	       
					        	            	    	}    
				        						    });
		        	            		 		      setTimeout(checkProgress, 0);
				        	            	    	
		        	     				 }
		        	            		 else
		        	            		 {
		        	            			 $('#warningwebtable2').modal('show');
		        	            		 }
		 						        	        
	 	     		        	 	});
		        	            	 
		        	            	 /*
									 "Задание P04"
									 */
		        	            	 $('#taskP04').click(function ()
	 	     		        	    {
		        	            	
		        	            		 if ($('.panel#tableexcel').is(':visible'))
		        	     				 {
		        	            		 		var hotInstance = $('#list1onsc').handsontable('getInstance');
		        	            		 		var myData ={list1:hotInstance.getData()  }
				        	            	    
				        	            	       $("#dim3").css("height", $(document).height());
			        	        	     		   $("#dim3").fadeIn();
			        	        	     		   spinner.spin($('#spinner_center2')[0]);
			        	        	        	   ajax_cnt++;
			        	        	        	   
						        	        	   $.ajax({
				        						        url: "TaskP04",
				        						        type: 'POST',
				        						        dataType: 'json',
				        						        data: JSON.stringify(myData),
				        						        contentType: 'application/json',
				        						        success: function ( data) {hotInstance.loadData(data.data1);$('#btnexportfromhandsontableTOExcel').trigger('click');},
				        						        complete: function(jqXHR, textStatus)
				        						        {
					        	            	    	       
					        	            	    	}    
				        						    });
		        	            		 		      setTimeout(checkProgress, 0);
				        	            	    	
		        	     				 }
		        	            		 else
		        	            		 {
		        	            			 $('#warningwebtable2').modal('show');
		        	            		 }
		 						        	        
	 	     		        	 	});	
		        	            	 
		        	            	 
								/*
									функция исполняет работу прогресс бара
								*/
	        	            	 function checkProgress() {
		        	            	   
		        	            	    $.ajax({
	        						        url: "ProgressBar",
	        						        type: 'POST',
	        						        dataType: 'json',
	        						        contentType: 'application/json',
	        						        success: function ( dat)
	        						         {
	        						        	console.log('cP '+dat);
	        						        	 $('#testprogressbar').text(dat + "%");
	        						        	 $('#testprogressbar').width(dat + "%");
	        						        	if (parseInt(dat) < 100) {
			        	            	            setTimeout(checkProgress, 250); // Checks again after one second.
			        	            	        }
	        						        	else
		        						        {    
	        						        		setTimeout("$('#dim3').fadeOut();spinner.stop();ajax_cnt = 0;",2000);
	        						        		setTimeout("$('#testprogressbar').css('text', '0%');$('#testprogressbar').css('width', '0%');",2500);
		        						        }
	        						         },
  						                    complete: function(jqXHR, textStatus){ }    
	        						    });
		        	             }
	        	 			 


	        	            		        							        	            
	        	 			  /*Функция обрабатывает нажатие клавиши рассформировать запрос
	        	 			*/
	        	 				    $('#zaprosWebExcelCancel').click(function ()
	        	 					{
	        	 				    	 var hotInstance = $('#list1onsc').handsontable('getInstance');
		        	            	     var myData ={disband:hotInstance.getData()  }

		        	            	     $("#dim3").css("height", $(document).height());
	        	        	     		 $("#dim3").fadeIn();
	        	        	     		 spinner.spin($('#spinner_center2')[0]);
	        	        	        	 ajax_cnt++;
								       	 
			        	            	 $.ajax({
		        						        url: "DelEmptyRowsFromWebExcel",
		        						        type: 'POST',
		        						        dataType: 'json',
		        						        data: JSON.stringify(myData),
		        						        contentType: 'application/json',
		        						        success: function ( data)
		        						        {
			        						        hotInstance.loadData(data.data1);
			        						        // включаем кнопку сформированного запроса
			        	            	 			$('#zaprosWebExcel').prop('disabled', true);
			        	            	 			$('#zaprosWebExcelCancel').prop('disabled', true);
			        	            	 			// обновляем кнопку: имя запроса 
			        	            	 			$('#zaprosWebExcel').text('Запрос не сформирован');
			        	            	 			$('#zaprosWebExcel').prop('value', '');
			        						    },
		        						        complete: function(jqXHR, textStatus) {}    
		        						 });

			        	            	 setTimeout(checkProgress, 2000);	 	 
	        	 					});
	        	 			 
// КНОПКА ZPAJAX
/*
 * Общая логика кнопки zpajax :
 * При нажатии на  кнопку  zp в меню запросы ффомс переходим на серверную сторону где  в wsAnswer ловим ДВА ПОДРЯД КЛЮЧА (сообщения) 
 * 1) Zp1Ajax - говорим серверу что хотим выполнить кусок кода который 
 * 2) json формат таблицы с первогоо листа который содержит все енп которые мы хотим отправить в запросе федералам 
 */
$('#ZP1Ajax').click(function ()
	     { 
				$("#modalZP1beta").modal('hide');
				$('#modalfoms').modal('hide');
       	 		setTimeout ("$('ul#login-dp').slideDown(2000);", 1000);  	
				$('#tamessage').val("> Послан запрос "+this.value+"...");
				// отправляем ключ что это такой-то запрос
				searchInDirectory('Zp1Ajax');
				// объект 1-го листа
				var hotInstan1 = $('#list1onsc').handsontable('getInstance');
				// вычисляем количество строк
				/*	var e1 =hotInstan1.countRows()-hotInstan1.countEmptyRows(true);
					var wd1 = hotInstan1.countCols()-hotInstan1.countEmptyCols(true);
					//затираем
					for(var i=1; i<e1;i++)
            	 		{
	 						for(var j=1; j<wd1;j++)
	            	 		{
	 							hotInstan1.setDataAtCell(i,j,'');
    	            	 	}
	            	 	}
				// вычисляем количество на первом листе первого столбца
			*/	var e1 =hotInstan1.countRows()-hotInstan1.countEmptyRows(true);
				// упаковываем данные
				var myData = { Zp1Ajax:hotInstan1.getData(1,0,e1-1,0)}
				// преобразуем в jason (не обязательно)
				var r = JSON.stringify(myData);
				// отправляем по сокету
		        searchInDirectory(r);
   	     });
   	     
$('#A08P14').click(function ()
	     { 
	 		if ($('.panel#tableexcel').is(':visible')) 
	 		{
			    var hotInstance	 = $('#list1onsc').handsontable('getInstance');
				var hotInstance2 = $('#list2onsc').handsontable('getInstance');
				var hotInstance3 = $('#list3onsc').handsontable('getInstance'); 
				
				// определяем количество строк
				var e1 =hotInstance.countRows()-hotInstance.countEmptyRows(true);
				var wd1 = hotInstance.countCols()-hotInstance.countEmptyCols(true);
				
				var e2 =hotInstance2.countRows()-hotInstance2.countEmptyRows(true);
				var wd2 = hotInstance2.countCols()-hotInstance2.countEmptyCols(true);
				
				var e3 =hotInstance3.countRows()-hotInstance3.countEmptyRows(true);
				var wd3 = hotInstance3.countCols()-hotInstance3.countEmptyCols(true);
		
				$('#modalfoms').modal('hide');
      	 		
				
				var myDatap02 = { list1:hotInstance.getData(0,0,e1-1,wd1-1), list2:hotInstance2.getData(0,0,e2-1,wd2-1), list3:hotInstance3.getData(0,0,e3-1,wd3-1)	}	
				console.log('ffff '+JSON.stringify(myDatap02));
		 		$("#dim2").css("height", $(document).height());
	     		$("#dim2").fadeIn();
	     		spinner.spin($('#spinner_center')[0]);
	        	 ajax_cnt++;
    	    
		        $.post('A08P14url',JSON.stringify(myDatap02),function(res)
  	                {
		        			hotInstance.loadData(res.list1);
      	            },'json')
		         .done(function(ev)
		   	 	{
		        		$('#btnexportfromhandsontableTOExcel').trigger('click');
		        	 	$('#dim2').fadeOut();spinner.stop();ajax_cnt = 0;
		        		//if(ev.info > 0){console.log('info '+ev.info); setTimeout ("$('#warning1').modal('show');", 2000);}
	    	 			// включаем кнопку сформированного запроса
	    	 			$('#zaprosWebExcel').prop('disabled', false);
	    	 			// включаем кнопку расформировать запрос
	    	 			$('#zaprosWebExcelCancel').prop('disabled', false);
	    	 			// обновляем кнопку: имя запроса 
	    	 			$('#zaprosWebExcel').text('Отправить '+'A08P14');
	    	 			// устанавливаем value кнопке запрос несформирован или сформирован
	    	 			$('#zaprosWebExcel').prop('value', 'A08P14');
    	 			
    	 			
		        	 
	 	   	     })
	 	   		 .error(function(msg) {$('#dim2').fadeOut();spinner.stop();ajax_cnt = 0; alert(' Произошла ошибка загрузки. Обновитесь и повторите.');});
	 		}		
  	     });   	 

$('#A03P07').click(function ()
	     { 
	 		if ($('.panel#tableexcel').is(':visible')) 
	 		{
	 			
				$('#modalfoms').modal('hide');
       	 		setTimeout ("$('ul#login-dp').slideDown(2000);", 1000);  	
				$('#tamessage').val("> Послан запрос "+this.value+"...");
				// отправляем ключ что это такой-то запрос
				searchInDirectory('A03P07');
				// объект 1-го листа
				var hotInstan1 = $('#list1onsc').handsontable('getInstance');
				var e1 =hotInstan1.countRows()-hotInstan1.countEmptyRows(true);
				// упаковываем данные
				var myData = { list1:hotInstan1.getData(1,0,e1-1,0)}
				// преобразуем в jason (не обязательно)
				var r = JSON.stringify(myData);
				// отправляем по сокету
		        searchInDirectory(r);	 			
	 		}		
 	     });   	 
   	     
   	     
$('#buttonZP3').click(function ()
	     { 
			console.log('sfdfsdfsdfd');
	
	 		if ($('.panel#tableexcel').is(':visible')) 
	 		{
	 			
				$('#modalfoms').modal('hide');
      	 		setTimeout ("$('ul#login-dp').slideDown(2000);", 1000);  	
				$('#tamessage').val("> Послан запрос "+this.value+"...");
				// отправляем ключ что это такой-то запрос
				searchInDirectory('ZP3');
				// в качестве второго индификационного флага
		        searchInDirectory('ZP3secondpart');	 			
	 		}		
	     });   	 

$('#ZP1taskA8P4').click(function ()
	     { 
	
				$("#modalZP1beta").modal('hide');
      	 		$('#modalfoms').modal('hide');
      	 		setTimeout ("$('ul#login-dp').slideDown(2000);", 1000);  	
				$('#tamessage').val("> Послан запрос "+this.value+"...");
				// отправляем ключ что это такой-то запрос
				searchInDirectory('Zp1taskA8P4');
				// объект 1-го листа
				var hotInstan1 = $('#list1onsc').handsontable('getInstance');
			
				
				// вычисляем количество на первом листе первого столбца
				var e1 =hotInstan1.countRows()-hotInstan1.countEmptyRows(true);
				// упаковываем данные
				var myData = { Zp1taskA8P4:hotInstan1.getData(1,0,e1-1,0)}
				// преобразуем в jason (не обязательно)
				var r = JSON.stringify(myData);
				// отправляем по сокету
		        searchInDirectory(r);
  	     });

//Скрипт выпадающего меню. Кнопка запросы 
	
			$('a#zapros').click(function ()
			{
				// прогрузка  имени пользователя для сокета серверной части
				var vr = '<%=user%>';
				//alert('user-'+ vr);
				transmitUserName(vr);
				
					$('ul#dr').slideToggle('slow'); 
// если открыт(флаг nowcount=1) то прячем все кнопки чтобы не тыкали пока выполняется запрос
					if(getNowCount())
					{
						$('button#menuindex').hide();
						$('button#menuindexwsYN').hide();
					}
					else
						{
						$('button#menuindex').show();
						$('button#menuindexwsYN').show();
						}
			 });		

// выдающее меню другие запросы
	
$('a#zapros2').click(function ()
		{$('ul#dr2').slideToggle('slow');    });
$('a#drugoe').click(function ()
		{$('ul#dr3').slideToggle('slow');    });		
$('a#settings').click(function ()
		{$('ul#settingsU').slideToggle('slow');    });				


// при нажатии кнопки запрос фио д  в выпадающем меню выдает модальное окно и закрывает выпадающее меню
// КНОПКА ДРУГИЕ ЗАПРОСЫ
$('button#drugiezaprosi').click(function ()
		{$('ul#dr2').slideToggle('slow');    });	


// при нажатии на клавишу запроса 
	
	// при нажатии других кнопок из запросы фомс
	$('button#menuindex').click(function ()
			{ 

		
				$('#modalfoms').modal('hide'); 
				if(searchInDirectory(this.value))
					{
						setTimeout ("$('ul#login-dp').slideDown(2000);", 1000);  	
						$('#tamessage').val("> Послан запрос "+this.value+"...");
						
					}			
			});	
	// при нажатии кнопки ZP1 (старое)
	$('button#menuindexws').click(function ()
			{ 
					
		
				$('#modalfoms').modal('hide'); 
				if(searchInDirectory(this.value))
					{
						setTimeout ("$('ul#login-dp').slideDown(2000);", 1000);  	
						$('#tamessage').val("> Послан запрос "+this.value+"...");
						
					}			
			});	
// кнопка статус запроса 
	$('a#statuszaprosa').click(function ()
			{
				setTimeout ("$('ul#login-dp').slideToggle('slow');", 50);  		
			});	
	




/*
 * при нажатии на zp1 с выбором вывода экселя(да/нет)
 * 
 */ 
	
	var  zap;
	$('button#menuindexwsYN').click(function ()
			{	searchInDirectory(this.value); zap = this.value;
			// если при нажатии zp1 открыта таблица то она обновляет эксель
				if ($('.panel#tableexcel').is(':visible'))
				{
					/* БЛОКИРУЮ ТК РАБОТАТЬ С ЭКСЕЛЕМ И КНОПКОЙ ТАБЛИЦА НЕ НАДО
							 
							 var hot1 = $('#list1onsc').handsontable('getInstance');
		 	            	 var hot2 = $('#list2onsc').handsontable('getInstance');
		 	            	 var hot3 = $('#list3onsc').handsontable('getInstance');
							// экспорт с handsontable в excel
		        	 		var e1 =hot1.countRows()-hot1.countEmptyRows(true);
		        	 		var wd1 = hot1.countCols()-hot1.countEmptyCols(true);
		        	 		var e2 =hot2.countRows()-hot2.countEmptyRows(true);
		        	 		var wd2 = hot2.countCols()-hot2.countEmptyCols(true);
		        	 		var e3 =hot3.countRows()-hot3.countEmptyRows(true);
		        	 		var wd3 = hot3.countCols()-hot3.countEmptyCols(true);
		        	 		
		        	 		var myData = { list1:hot1.getData(0,0,e1-1,wd1-1), list2:hot2.getData(0,0,e2-1,wd2-1),list3:hot3.getData(0,0,e3-1,wd3-1),gouser  }
		        	 		$.ajax({
						        type: 'POST',
						        url: "ExportToExcelFromEmdedTable",
						        data: JSON.stringify(myData),
						        //contentType: 'application/json',
						        success: function (response, textStatus, jqXHR) {console.log(response, textStatus, jqXHR) ;  },
							    //capture the request before it was sent to server
					            beforeSend: function(jqXHR, settings)
					            {
					            },
							    //this is called after the response or error functions are finsihed
					            //so that we can take some action
					            complete: function(jqXHR, textStatus)
					            {
					            	var val = $('button#menuindexwsNO').attr("value");
				        	 		searchInDirectory(val);
				    				$('#modalfoms').modal('hide');
				    				setTimeout ("$('ul#login-dp').slideDown(2000);", 1000);  	
				    				$('#tamessage').val("> Послан запрос "+zap+"...");
				    				console.log('2'+ val+' '+zap) ;
					            },
					            dataType: 'json'
						    }); */
		  	              /*  $.post('ExportToExcelFromEmdedTable',JSON.stringify(myData),function(res)
		  	                {
		  	                	console.log(res) ;
		        	 			
		      	            },'json');*/
		      	            
      	            
	     		}
				else
					{
					var val = $('button#menuindexwsNO').attr("value");
        	 		searchInDirectory(val);
    				$('#modalfoms').modal('hide');
    				setTimeout ("$('ul#login-dp').slideDown(2000);", 1000);  	
    				$('#tamessage').val("> Послан запрос "+zap+"...");
    				console.log('2'+ val+' '+zap) ;
					}
			 });
/* отключил модальное повыбору да.нет
	$('button#menuindexwsYES').click(function ()
			{	
				searchInDirectory(this.value);
				$('#modalZP1').modal('hide');
				$('#modalfoms').modal('hide');

				setTimeout ("$('ul#login-dp').slideDown(2000);", 1000);  	
				$('#tamessage').val("> Послан запрос "+zap+"...");
				 
		   });	

	$('button#menuindexwsNO').click(function ()
			{	
				searchInDirectory(this.value);
				$('#modalZP1').modal('hide');
				$('#modalfoms').modal('hide');

				setTimeout ("$('ul#login-dp').slideDown(2000);", 1000);  	
				$('#tamessage').val("> Послан запрос "+zap+"...");
			});			
*/

	// проверяем чекбокс настроект xml
	var c = document.querySelector('#chechboxotladka');
	c.onclick = function() {
	 if (c.checked) {
		 postToServerOtladkaXMLYES()
	 } else {
		 postToServerOtladkaXMLNO()
	 }
	}

	//postToServerOtladkaXMLNO();
});

$(document).ready(function()
		{ 
				var vid = document.getElementById("bgindex");
				vid.playbackRate = 0.5;

		});



</script>

</head>
<body>
<video id="bgindex" src="image/parish.mp4" autoplay="true" loop="true" muted="true" style="width:100%; position: absolute;"></video>

	<div class="conteinerHeader">
		<!-- Заставка -->
		<div class="splash">
			<div class="container">
				<div class="row">
					<div class="col-lg-3">
						<div class="media" style="color: #0B0B3B">
							<a href="#" class="pull-left"><img class="img-circle"
								src="image/user2.png"></a>
							<div class="media-body">
								<h4 class="media-heading"><%=user%><br> <small
										style="color: #0B0B3B"><i><%=createTime%></i></small>
								</h4>
								<p>
									Компьютер:
									<%=usercomp%></p>
							</div>
						</div>
					</div>

					<div class="col-lg-7 col-lg-offset-1" style="color: #0B0B3B">
						<a href="#" class="pull-right"><i class="fa fa-desktop fa-1x"></i>
							IP адрес:<%=ip%></a><br> <a href="#" class="pull-right"><i
							class="fa fa-desktop fa-1x"></i> ID сессии:<%=ses%> </a> <br>
						<br> <a href="exit" class="pull-right" style="color: black;"><i
							class="fa fa-sign-out fa-2x"></i></a>
						<div class="row">
							<div class="col-lg-1 col-lg-offset-10 ">
								<!-- <a href="#" class="pull-right" style="color: black;"><i class="fa fa-envelope-o fa-2x"></i></a>  -->
							</div>

						</div>

					</div>
				</div>
			</div>
		</div>

		<!-- /Заставка -->


		<nav class="navbar navbar-default navbar-inverse" role="navigation">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>

			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><a href="loadGoznakError" id="errorgz"><i class="fa fa-warning faa-flash animated-hover"></i> Ошибки Гознака</a></li>
					<li><a href="chart.jsp" id="errorgz"><i class="fa fa-bar-chart" aria-hidden="true"></i> Инфографика</a></li>
					<li><a href="#" data-toggle="modal"
						data-target=".bs-example-modal-lg" id="zapros"><i
							class="fa fa-space-shuttle faa-passing animated-hover"></i>
							Запросы ФОМС</a></li>
					<div class="modal fade bs-example-modal-lg" tabindex="-1" id="modalfoms" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
						<div class="modal-dialog modal-lg">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true">&times;</button>
									<h3 class="modal-title">Выберите запрос</h3>
								</div>

								<div class="row">
									<div class="col-lg-4">
										<li>
											<!-- 	<form id = "A08p03Create" method = "post" action="message">
										<input type = "hidden" name = "mestype" value = '3'>
										<input type = "hidden" name = "username" value = '<c:out value="${sessionScope.username}" />'>
								 -->
											<button class="btn btn-primary" id="menuindex"
												title="Смена СМО со сменой страховки" value="A08P03">А08П03</button>
											<!-- 	</form>	  -->
										</li>
										<li>
											<!-- 	<form id = "A08p16Create" method = "post" action="message">
										<input type = "hidden" name = "mestype" value = '116'>
										<input type = "hidden" name = "username" value = '<c:out value="${sessionScope.username}" />'>
								 -->
											<button class="btn btn-primary" id="menuindex"
												title="Исправление данных, например ЕНП" value="A08P16">А08П16</button>
											<!--  	</form> -->
										</li>
										<li>
											<!-- 	<form id = "A13p09Create" method = "post" action="message">
										<input type = "hidden" name = "mestype" value = '9'>
										<input type = "hidden" name = "username" value = '<c:out value="${sessionScope.username}" />'>
								 -->
											<button class="btn btn-primary" id="menuindex"
												title="Отмена сообщения о смерти" value="A13P09">А13П09</button>
											<!-- 	</form>	 -->
										</li>
										<li>
											<!-- 	<form id = "A08p03prCreate" method = "post" action="message">
										<input type = "hidden" name = "mestype" value = '3pr'>
										<input type = "hidden" name = "username" value = '<c:out value="${sessionScope.username}" />'>
								 -->
											<button class="btn btn-primary" id="menuindex"
												title="Смена СМО со сменой страховки с предыдущими ФИО и ДР"
												value="A08P03pr">А08П03pr</button> <!-- 	</form>  -->
										</li>
										<li>
											
											<button class="btn btn-primary" id="menuindex"
												title="Смена данных УДЛ без смены страховки" value="A08P04">А08П04</button>
											<!-- 		</form>  -->
										</li>
										<li>
											
											<button class="btn btn-primary" id="menuindex"
												title="Объединение дубликатов общее" value="A24P10com">А24П10com</button>
										
										</li>
										<li>
											
											<button class="btn btn-primary" id="menuindex"
												title="Начало передачи в ЦС ЕРЗ сведений о занятости застрахованных лиц"
												value="P26">П26</button>
										</li>
										<li>
											
											<button class="btn btn-primary" id="menuindex"
												title="Окончание передачи в ЦС ЕРЗ сведений о занятости застрахованных лиц"
												value="P27">П27</button>

										</li>
									</div>
									<div class="col-lg-4">
										<li>
											
											<button class="btn btn-primary" id="menuindex"
												title="Открытие страховки" value="A08P01">А08П01</button>
										</li>
										<li>
											<!-- 	<form id = "A08p06Create" method = "post" action="message">
										<input type = "hidden" name = "mestype" value = '6'>
										<input type = "hidden" name = "username" value = '<c:out value="${sessionScope.username}" />'>
								 -->
											<button class="btn btn-primary" id="menuindex"
												title="Смена страховки без смены СМО" value="A08P06">А08П06</button>
											<!-- 	</form>	 -->
										</li>
										<li>
											<!-- 	<form id = "A24p10Create" method = "post" action="message">
										<input type = "hidden" name = "mestype" value = '110'>
										<input type = "hidden" name = "username" value = '<c:out value="${sessionScope.username}" />'>
								 -->
											<button class="btn btn-primary" id="menuindex"
												title="Объединение дубликатов" value="A24P10">А24П10</button>
											<!-- 	</form>  -->
										</li>
										<li>
											
											<button class="btn btn-primary" id="menuindex"
												title="Запрос страховой принадлежности по предыдущим ФИО"
												value="ZP1pr">ZP1pr</button> 
										</li>
										<li>
											
											<button class="btn btn-primary" id="menuindex"
												title="Объединение дубликатов с другим главным ЕНП"
												value="A24P102">А24П102</button>
										</li>
										<li>
											
										 	<button class="btn btn-primary" id="menuindex"
												title="Запрос страховой принадлежности по ФИОД и УДЛ"
												value="ZP1Fiod">ZP1Fiod</button> 
										</li>
										<li>
											<!-- 	<form id = "A08v01Create" method = "post" action="message">
											<input type = "hidden" name = "mestype" value = '1v'>
											<input type = "hidden" name = "username" value = '<c:out value="${sessionScope.username}" />'>
									 -->
											<button class="btn btn-primary" id="menuindex"
												title="Изменение информации о застрахованном лице"
												value="A08B01">А08B01</button> <!-- 	</form>  -->
										</li>
									</div>
									<div class="col-lg-4">
										<li>
											<button class="btn btn-primary" id="menuindex" title="Снятие с учета"  value="A08P02">А08П02</button>
										</li>
									</div>
									<div class="col-lg-4">
										<li>
											<button class="btn btn-primary" id="menuindex" title="Смена СМО со сменой страховки при коллизии" value="A08P03kol">А08П03kol</button> 
										</li>
										<li>
											<!-- <button class="btn btn-primary" id="menuindex" title="Сообщение о смерти" value="A03P07">А03П07</button> -->
										</li>	
										<li>
											<button class="btn btn-primary" id="menuindex" title="Проставить дату окончания иностранцу" value="A08P03For">А08П03For</button> 
										</li>
										<li>
											<button style="background-color: #4193ab;" class="btn btn-primary" id="buttonA08P02" title="Закрытие страховки, Открепить ЗЛ иностранца" value="A08P02test" data-toggle="modal" data-target="#modalA08P02">А08П02test</button>
										</li>
										
										<div class="modal fade" id="modalA08P02" role="dialog"
											aria-labelledby="myModalLabel" aria-hidden="true">
											<div class="modal-dialog">
												<div class="modal-content">
													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal"
															aria-hidden="true">&times;</button>
														<h3 class="modal-title">Конструктор запроса A08P02</h3>
														<p>Выбирете какой датой Вы хотите закрыть страховую
															принадлежность</p>
													</div>
													<div class="modal-body">
															<li>
																<button type="button" id="A08P02today" style="margin-bottom:7px;" class="btn btn-primary" title="Закрыть сегодняшней датой" value="0">ЗСД</button>
															</li>
															<li>
																<button type="button" id="A08P02howINsmo" style="margin-bottom:7px;" class="btn btn-primary" title="Закрыть датой как в СМО" value="0">ЗДкС</button>
															</li>
															<li>
																<button type="button" id="A08P02howINsmoPID29" style="margin-bottom:7px;" class="btn btn-primary"  title="Закрыть датой как в СМО с PID29" value="0">ЗДкСсPID29</button>	
															</li>
															<li>
																<button type="button" id="A08P02akaZP3" style="margin-bottom:7px;" class="btn btn-primary" title="Закрыть ZP3 -> A08П02" value="0">З_ZP3_A08П02</button>
															</li>
													</div>
												</div>
											</div>
										</div>											
										
										<li>
											<button style="background-color: #4193ab;" class="btn btn-primary" id="buttonZP9" title="Запрос истории страховых принадлежностей" value="ZP9" data-toggle="modal" data-target="#modalZP9">ZP9</button>
										</li>
										
											<div class="modal fade" id="modalZP9" role="dialog"
												aria-labelledby="myModalLabel" aria-hidden="true">
												<div class="modal-dialog">
													<div class="modal-content">
														<div class="modal-header">
															<button type="button" class="close" data-dismiss="modal"
																aria-hidden="true">&times;</button>
															<h3 class="modal-title">Конструктор запроса ZP9</h3>
															<p>Выбирите по какому условию вы хотите узнать страховую принадлежность </p>
														</div>
														<div class="modal-body">
															<p></p>
															<button type="button" id="ZP9enp"
																class="btn btn-primary" value="0">ЕНП</button>
															<button type="button" id="ZP9passport" class="btn btn-primary" value="0">Паспорт</button>
															<button type="button" id="ZP9snils" class="btn btn-primary" value="0">Снилс</button>
														</div>
													</div>
												</div>
											</div>		
										<li>
										<!-- <button type="button" id="A08P14" class="btn btn-primary" value="A08P14">А08П14</button> -->
										</li>
										<li>
											<button style="background-color: #4193ab;" class="btn btn-primary" id="buttonZP3" title="Запрос списка подлежащих откреплению" value="ZP3">ZP3</button>
										</li>
										<li>
										<button style="background-color: #4193ab;" type="button" id="A03P07" class="btn btn-primary" value="A03P07">А03П07</button>
										</li>
										<li>
										<button style="background-color: #4193ab;" type="button" id="A08P08" class="btn btn-primary" value="A08P08">А08П08</button>
										</li>
										<li>
											<!-- <button  class="btn btn-primary" id="ZP1Ajax"	value="ZP1Ajax">ZP1test</button> -->
										</li>
										<li>
											<button style="background-color: #4193ab;" class="btn btn-primary" id="buttonZP1"  data-toggle="modal" data-target="#modalZP1beta">ZP1beta</button>
										</li>
										
										<div class="modal fade" id="modalZP1beta" role="dialog"
											aria-labelledby="myModalLabel" aria-hidden="true">
											<div class="modal-dialog">
												<div class="modal-content">
													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal"
															aria-hidden="true">&times;</button>
														<h3 class="modal-title">Конструктор запроса ZP1</h3>
														<p>Выберите вид запроса</p>
													</div>
													<div class="modal-body">
														<p></p>
														<button  class="btn btn-primary" id="ZP1taskA8P4" title="Zp1 по паспорту,фио,др,полу,и месту рождения. Позволяет достоверно оценить наличие паспорта в ЦС "value="Zp1taskA8P4">ZP1 FioDPb</button>
														<button  class="btn btn-primary" id="ZP1Ajax"	value="ZP1Ajax">ZP1</button>
														
													</div>
												</div>
											</div>
										</div>	
										
										<li>
											<!-- !!!!!!!!!!!!!!!!!!! -->
											<button type="submit" class="btn btn-primary"
												id="menuindexwsYN" data-toggle="modal"
												data-target="#modalZP1" value="ZP1">ZP1</button>
										</li>
										<div class="modal fade" id="OTKLUCHILmodalZP1" role="dialog"
											aria-labelledby="myModalLabel" aria-hidden="true">
											<div class="modal-dialog">
												<div class="modal-content">
													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal"
															aria-hidden="true">&times;</button>
														<h3 class="modal-title">Внимание</h3>
														<p>Открыть Excel автоматически после окончания запроса</p>
													</div>
													<div class="modal-body">
														<p>Открыть Excel автоматически</p>
														<button type="button" class="btn btn-success"
															id="menuindexwsYES" value="1" disabled="disabled">Да</button>
														<button type="button" class="btn btn-danger"
															id="menuindexwsNO" value="0">Нет</button>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-primary"
										data-dismiss="modal">Закрыть окно</button>
								</div>
							</div>

						</div>
					</div>
					<li><a href="#" id="onofftable">Таблица</a></li>

					<!-- МЕНЮ ДРУГИЕ ЗАПРОСЫ ФОМС !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! -->
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						id="zapros2" data-toggle="dropdown"><i
							class="fa fa-spinner faa-spin animated-hover"></i> Другие запросы<span
							class="caret"></span></a>
						<ul class="dropdown-menu" id="dr2" role="menu">
							<li>
								<button class="btn btn-primary" id="drugiezaprosi"
									data-toggle="modal" data-target="#myModal">Запрос по ФИО Д</button>
								<button class="btn btn-primary" id="drugiezaprosi" data-toggle="modal" data-target="#myModal1">Запрос ЕНП-внеш -> Енп-вн</button>
								<button class="btn btn-primary" id="drugiezaprosi"
									data-toggle="modal" data-target="#myModal2">Исправление внеш ЕНП</button>
									<button class="btn btn-primary" id="drugiezaprosi" data-toggle="modal" data-target="#myModal3">Сгенерировать ЕНП</button>
									<button class="btn btn-primary" id="taskP03">Задание П03</button>
									<button class="btn btn-primary" id="taskP02">Задание П02</button>
									<button class="btn btn-primary" id="taskP04">Задание П04</button>
									<button class="btn btn-primary" id="processErrorGZ" data-toggle="modal" data-target="#myModalprocessErrorGZ">Обработка ошибок ГОЗНАКА</button>	
									<button class="btn btn-primary" id="refresh_tab_errorgz">Обновить ошибки ГОЗНАКА</button>	
									
							</li>
						</ul></li>
					<!-- КОНЕЦ ДРУГИЕ ЗАПРОСЫ -->
					<!--  Блок Импорт экселя на handsontable -->
					<div class="modal fade" id="importFromExcel" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button btn-primary" class="close"
										data-dismiss="modal" aria-hidden="true">&times;</button>
									<h3 class="modal-title">Импорт MS Excel в Web-таблицу</h3>
									<p>Импорт MS Excel в Web-таблицу</p>
								</div>
								<div class="modal-body">
									<form id="upload-form" action="ImportFromExcelToHandsontable" method="post" enctype="multipart/form-data">Выберите файл для загрузки формата .xls :<input type="file"name="fileName"> <br>
										<button type="submit" class="btnА btn-primary" id="activateGetJson" >Отправить запрос</button>
										<button type="button" class="btn btn-success" id="">Помощь</button>	
									</form>
									
									
									<div id="" style="margin-top: 15px"></div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-primary" data-dismiss="modal">Закрыть окно</button>
								</div>
							</div>
						</div>
					</div>
					<!-- конец Блок Импорт экселя на handsontable -->
					<!--  Блок Импорт упрак2 -->
					<div class="modal fade" id="importuprak2_mod" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button btn-primary" class="close"
										data-dismiss="modal" aria-hidden="true">&times;</button>
									<h3 class="modal-title">Импорт сообщения Uprak2 для web-таблицы</h3>
									<p>Импорт Uprak2</p>
								</div>
								<div class="modal-body">
									<form id="upload-form_uprak2" action="ImportUprak2" method="post" enctype="multipart/form-data">Выберите файл для загрузки формата .uprak2 :<input type="file"name="fileName"> <br>
										<button type="submit" class="btn btn-primary" id="activateGetJson2" >Отправить</button>
										<button type="button" class="btn btn-success" id="help_import_uprak2">Помощь</button>
									</form>
									
									
									<div id="text_help_import_uprak2" style="margin-top: 15px"></div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-primary" data-dismiss="modal">Закрыть окно</button>
								</div>
							</div>
						</div>
					</div>
					<!-- конец Блок Импорт упрак2 -->
					<!--  Блок Импорт zp3 и тд -->
					<div class="modal fade" id="importuprak2zp3_mod" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button btn-primary" class="close"
										data-dismiss="modal" aria-hidden="true">&times;</button>
									<h3 class="modal-title">Импорт сообщения  Uprak2  типа zp3  прочее для  web-таблицы</h3>
									<p>Импорт Uprak2</p>
								</div>
								<div class="modal-body">
									<input id="zp3filename" type="text"  data-role="tagsinput"  /> 
								<div class="modal-footer">
									<button type="button" class="btn btn-primary" data-dismiss="modal">Закрыть окно</button>
								</div>
							</div>
						</div>
					</div>
					<!-- конец Блок Импорт zp3 и тд -->
					<!-- Блок Запрос по ФИОД -->
					<div class="modal fade" id="myModal" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button btn-primary" class="close"
										data-dismiss="modal" aria-hidden="true">&times;</button>
									<h3 class="modal-title">Запрос по ФИО Д</h3>
									<p>Этот запрос позволяет по ФИОД получить внутренний ЕНП
										застрахованного.</p>

								</div>
								<div class="modal-body">
									<form action="FileUploadServlet" method="post"
										enctype="multipart/form-data">
										Выберите файл для загрузки формата .xls :<input type="file"
											name="fileName"> <br>
										<button type="submit" class="btn btn-primary">Отправить
											запрос</button>
										<button type="button" class="btn btn-success" id="su">Помощь</button>	
										
									</form>
									
									<div id="texthelpqueryfiod" style="margin-top: 15px"></div>


								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-primary"
										data-dismiss="modal">Закрыть окно</button>
								</div>
							</div>
						</div>
					</div>
					<!-- Конец Блок Запрос по ФИОД -->
							<!-- исправление ошибок гознака -->
					<div class="modal fade" id="myModalprocessErrorGZ" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button btn-primary" class="close"
										data-dismiss="modal" aria-hidden="true">&times;</button>
									<h3 class="modal-title">Исправление ошибок ГОЗНАКА</h3>
									<p>Исправление  мешка ошибок</p>
									Ошибки можно скачать с владки "Ошибки гознака" расположенного в панели меню. Потом закачать здесь, предварительно
									оставить те ошибки которые хотите чтобы обработались.

								</div>
								<div class="modal-body">
									<form  id="UploadForm" action="processerror" method="post" enctype="multipart/form-data"> Выберите файл для загрузки формата .xls :
										<input type="file"  id="myfile" onclick="myfilefun()" name="fileName"> <br>
											<div id="progressbox">
												<div id="progressbar"></div>
												<div id="percent">0%</div>
											</div><br>
											<div id="messageupload"></div><br>
											<div id="progressboxGZ" style="display: none;">
												<div id="progressbarprocessGZ"></div>
												<div id="percentprocessGZ">0%</div>
											</div><br>
											<div id="messageend"></div><br>
										<button type="submit" id="btnstarttask" class="btn btn-primary" >Загрузить задание</button>
									</form>
									
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-primary"
										data-dismiss="modal">Закрыть окно</button>
								</div>
							</div>
						</div>
					</div>
					<!-- КОНЕЦ исправление ошибок гознака -->
					<!-- Запрос ЕНП-внеш -> Енп-вн -->
					<div class="modal fade" id="myModal1" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button btn-primary" class="close"
										data-dismiss="modal" aria-hidden="true">&times;</button>
									<h3 class="modal-title">Запрос ЕНП</h3>
									<p>Этот запрос позволяет по внешнему ЕНП получить
										внутренний ЕНП застрахованного.</p>

								</div>
								<div class="modal-body">
									<form action="OutFileUploadServlet" method="post"
										enctype="multipart/form-data">
										Выберите файл для загрузки формата .xls :<input type="file"
											name="fileName"> <br>
										<button type="submit" class="btn btn-primary">Отправить
											запрос</button>
										<button type="button" class="btn btn-success" id="su1">Помощь</button>
									</form>

									<div id="texthelpqueryoutenp" style="margin-top: 15px"></div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-primary"
										data-dismiss="modal">Закрыть окно</button>
								</div>
							</div>
						</div>
					</div>
					<!-- КОНЕЦ Запрос ЕНП-внеш -> Енп-вн -->
					<!-- Запрос Исправление внешнего ЕНП -->
					<div class="modal fade" id="myModal2" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button btn-primary" class="close"
										data-dismiss="modal" aria-hidden="true">&times;</button>
									<h3 class="modal-title">Запрос</h3>
									<p>Исправление внешнего ЕНП в нашей базе</p>

								</div>
								<div class="modal-body">
									<form action="EditOutEnp" method="post" enctype="multipart/form-data">
										Выберите файл для загрузки формата .xls :<input type="file"
											name="fileName"> <br>
										<button type="submit" class="btn btn-primary">Отправить запрос</button>
										<button type="button" class="btn btn-success" id="su2">Помощь</button>
									</form>

									<div id="texthelpquerydivision" style="margin-top: 15px">
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-primary"
										data-dismiss="modal">Закрыть окно</button>
								</div>
							</div>
						</div>
					</div>
					<!-- КОНЕЦ исправление внешнего енп -->
					<!-- Запрос Генерирование ЕНП -->
					<div class="modal fade" id="myModal3" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button btn-primary" class="close"
										data-dismiss="modal" aria-hidden="true">&times;</button>
									<h3 class="modal-title">Запрос</h3>
									<p>Генерирует ЕНП (без вставки в базу данных)</p>

								</div>
								<div class="modal-body">
									<form action="GenEnp" method="post" enctype="multipart/form-data">
										Выберите файл для загрузки формата .xls :<input type="file"
											name="fileName"> <br>
										<button type="submit" class="btn btn-primary">Отправить запрос</button>
										<button type="button" class="btn btn-success" id="">Помощь</button>
									</form>

									<div id="genEnp" style="margin-top: 15px">
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-primary"
										data-dismiss="modal">Закрыть окно</button>
								</div>
							</div>
						</div>
					</div>
					<!-- КОНЕЦ генерирования енп -->
					
					<!-- ОБНОВЛЕНИЯ -->
					<% if(user.equals("maltsekaterina")){ %>
				<!-- <div class="modal fade" id="updateonsite" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button btn-primary" class="close"
										data-dismiss="modal" aria-hidden="true">&times;</button>
									<h3 class="modal-title"></h3>
									<p></p>

								</div>
								<div class="modal-body">
								<img src="image/maxresdefault.jpg"  height="500" width="500">
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-success"
										data-dismiss="modal">Спасибо :) !!!</button>
								</div>
							</div>
						</div>
					</div> -->	
				<%	} %>
					<!-- КОНЕЦ ОБНОВЛЕНИЯ -->
					<!-- Предупреждение при формировании запроса на первом листе -->
					<div class="modal fade" id="warning1" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button btn-primary" class="close"
										data-dismiss="modal" aria-hidden="true">&times;</button>
									<h3 class="modal-title">Предупреждение</h3>
									<p></p>

								</div>
								<div class="modal-body">
									Вы пытаетесь закрыть страховую принадлежность людям которые
									либо уже закрыты либо страховая принадлежность человека не за
									нашим окато (50000). Внимательно посмотрите страховую
									принадлежность в запросе ZP1 (третья страница) и примите
									решение индивидуально по этим людям.
									<p>Вы можете вручную проставить данные по таким людям! И
										попытаться их закрыть. Для этого нажмите "Закрыть" и
										проставьте данные.</p>
									<p>Кнопка "удалить из запроса" автоматически удаляет всех у
										кого не заполнились данные.</p>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-success"
										data-dismiss="modal">Закрыть</button>
									<button type="button" class="btn btn-success"
										data-dismiss="modal" id="delrowsA08P02">Удалить из
										запроса</button>
								</div>
							</div>
						</div>
					</div>
					<!-- КОНЕЦ Предупреждение при формировании запроса на первом листе -->
					<!-- ОКНО ПРЕДУПРЕЖДЕНИЯ ЧТО ЗАПРОС ВЫПОЛНЯЕТСЯ И ТАБЛИЦУ НЕЛЬЗЯ ЗАКРЫТЬ ИЛИ ОТКРЫТЬ -->
					<div class="modal fade" id="warningwebtable" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button btn-primary" class="close"
										data-dismiss="modal" aria-hidden="true">&times;</button>
									<h3 class="modal-title">Предупреждение</h3>
									<p></p>

								</div>
								<div class="modal-body">Нельзя ОТКРЫТЬ или ЗАКРЫТЬ таблицу
									пока выполняется запрос! Дождитесь окончания запроса!</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-success"
										data-dismiss="modal">Закрыть окно</button>
								</div>
							</div>
						</div>
					</div>
					<!--  ОКНО ПРЕДУПРЕЖДЕНИЯ ЧТО ЗАПРОС ВЫПОЛНЯЕТСЯ И ТАБЛИЦУ НЕЛЬЗЯ ЗАКРЫТЬ ИЛИ ОТКРЫТЬ -->
					<!-- ОКНО ПРЕДУПРЕЖДЕНИЯ ЧТО надо открыть окно таблица -->
					<div class="modal fade" id="warningwebtable2" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button btn-primary" class="close"
										data-dismiss="modal" aria-hidden="true">&times;</button>
									<h3 class="modal-title">Предупреждение</h3>
									<p></p>

								</div>
								<div class="modal-body">Откройте окно таблица</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-success"
										data-dismiss="modal">Закрыть окно</button>
								</div>
							</div>
						</div>
					</div>
					<!--  ОКНО ПРЕДУПРЕЖДЕНИЯ ЧТО ЗАПРОС ВЫПОЛНЯЕТСЯ И ТАБЛИЦУ НЕЛЬЗЯ ЗАКРЫТЬ ИЛИ ОТКРЫТЬ -->

					<li class="dropdown"><a href="#" class="dropdown-toggle"
						id="drugoe" data-toggle="dropdown">Разное<span class="caret"></span></a>
						<ul class="dropdown-menu" id="dr3" role="menu"
							style="font-family: Times New Roman">
							<li>

								<h4 class='heads' style="color: black;">Zu2 имя файла</h4>
								<form name="zu2Load" method="post" action="loadZu2">
									<input class='editfile' type="text" name="zu2File" id='zu2File'
										onkeypress='checkNumber'> <input type="submit"
										value="Zu2Load"> <input type="hidden" name="username"
										value='<c:out value="${sessionScope.username}" />'>
								</form> <br>
								<h4 class='heads'>Zp имя файла</h4>
								<form name="zpLoad" method="post" action="loadZp">
									<input class='editfile' type="text" name="zpFile" id='zpFile'
										onkeypress='checkNumber'> <input type="submit"
										value="ZpLoad"> <input type="hidden" name="username"
										value='<c:out value="${sessionScope.username}" />'>
								</form> <br>
								<h4 class='heads'>Appak имя файла с расширением</h4>
								<form name="appakLoad" method="post" action="loadAppak">
									<input class='editfile' type="text" name="appakFile"
										id='appakFile' onkeypress='checkNumber'> <input
										type="submit" value="AppakLoad"> <input type="hidden"
										name="username"
										value='<c:out value="${sessionScope.username}" />'>
								</form> <br>

								<h4 class='heads'>Error имя файла с расширением</h4>
								<form name="errorLoad" method="post" action="loadError">
									<input class='editfile' type="text" name="errorFile"
										id='errorFile' onkeypress='checkNumber'> <input
										type="submit" value="ErrorLoad"> <input type="hidden"
										name="username"
										value='<c:out value="${sessionScope.username}" />'>
								</form> <!-- 
									<br>	
									<h4 class = 'heads'>Редактирование задания в Excel</h4>
										<a class = 'links' href= '<c:out value= '${sessionScope.username}.xls' />'>  Редактировать</a>
									
										<form>
											<input type = "hidden" id = "oneTimeTask" value = '<c:out value="${sessionScope.oneTimeTask}" />' >
											<input type = "hidden" id = "taskNumber" value = '<c:out value="${sessionScope.taskNumber}" />' >
											<input type = "hidden" id = "username" value = '<c:out value="${sessionScope.username}" />'>
										</form>
								-->

							</li>
						</ul></li>
					<%
						if (user.equals("ponomarev")) {
					%>

					<li class="dropdown"><a href="#" class="dropdown-toggle"
						id="settings" data-toggle="dropdown">Настройки<span
							class="caret"></span></a>
						<ul class="dropdown-menu" id="settingsU" role="menu"
							style="font-family: Times New Roman; min-width: 250px;">
							<li>
								<!--  <input type="checkbox" name="otladkacheckbox" value="a2"><h2>Отладка XML запроса</h2>-->
								<input type="checkbox" id="chechboxotladka" />&nbsp;&nbsp;Отладка
								xml запросов

							</li>

						</ul></li>
					<%
						}
					%>

				</ul>

				<ul class="nav navbar-nav navbar-right">

					<li class="dropdown"><a href="#" id="statuszaprosa"
						class="dropdown-toggle" data-toggle="dropdown"><b>Статус
								запроса</b> <span class="caret"></span></a>
						<ul id="login-dp" class="dropdown-menu">
							<li>
								<div class="row">
									<div class="col-md-12">
										<div class="social-buttons">
											<a href="#" id="btnload" class="btn btn-tw">Информация о
												запросе</a>
											<textarea id="tamessage" rows="10" readonly>
                                        	
											</textarea>
										</div>

									</div>

								</div>
							</li>
						</ul></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid --> </nav>


		<!-- /.row -->
		<div class="row">
			<!-- /.col-lg-6 -->
			<div class="col-lg-10 col-lg-offset-1">
				<div class="panel panel-primary" id="tableexcel" style="opacity: 1;">
					<div class="panel-heading">Таблица</div>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<!-- Nav tabs -->
						<ul class="nav nav-pills">
<!-- 							<li class="active"><a href="#home-pills" data-toggle="tab">Первая
									страница</a></li>
							<li><a href="#profile-pills" data-toggle="tab">Вторая
									страница</a></li>
							<li><a href="#messages-pills" data-toggle="tab">Третья
									страница</a></li>
							<li><a href="#settings-pills" data-toggle="tab">Четвертая
									страница</a></li> 
							<li><a href="#help-pills" data-toggle="tab">Помощь</a></li>-->
							
							<div class="well well-sm">
								<button type="button" class="btn btn-success btn-sm" id="btnexportfromhandsontableTOExcel">Экспорт</button>
								<button class="btn btn-primary btn-sm" id="btnimportfromhandsontableTOExcel" data-toggle="modal" data-target="#importFromExcel">Импорт</button>
								<button class="btn btn-primary btn-sm" id="btn_import_uprak2" data-toggle="modal" data-target="#importuprak2_mod">Импорт uprak2</button>
								<button class="btn btn-primary btn-sm" id="btn_import_uprak2" data-toggle="modal" data-target="#importuprak2zp3_mod">Импорт ZP3 uprak2</button>
								<button type="button" class="btn btn-success btn-sm" id="zaprosWebExcel">Запрос не сформирован</button>
								<button type="button" class="btn btn-success btn-sm" id="zaprosWebExcelCancel">Расформировать запрос</button>
							</div>
						</ul>

						<!-- Tab panes -->
						<div class="tab-content">
							<div class="tab-pane fade in active" id="home-pills">
								<h4>Вставка данных</h4>
								<p>
								<div id="list1onscstyle" style="overflow: auto; margin-bottom:20px;">
									<div id="list1onsc"></div>
								</div>
								<div id="list2onscstyle" style="overflow: auto;  margin-bottom:20px;">
									<div id="list2onsc"></div>
								</div>
								<div id="list3onscstyle" style="overflow: auto;">
									<div id="list3onsc"></div>
								</div>
								</p>
							</div>
							<div class="tab-pane fade" id="profile-pills">
								<h4>Данные из нашей базы</h4>
								<p>
								<!-- <div id="list2onscstyle" style="overflow: auto;">
									<div id="list2onsc"></div>
								</div> -->
								</p>
							</div>
							<div class="tab-pane fade" id="messages-pills">
								<h4>Данные из ЦС ЕРЗ</h4>
								<p>
								<!-- <div id="list3onscstyle" style="overflow: auto;">
									<div id="list3onsc"></div>
								</div> -->
								</p>
							</div>
							<div class="tab-pane fade" id="settings-pills">
								<h4>Settings Tab</h4>
								<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit,
									sed do eiusmod tempor incididunt ut labore et dolore magna
									aliqua. Ut enim ad minim veniam, quis nostrud exercitation
									ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis
									aute irure dolor in reprehenderit in voluptate velit esse
									cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat
									cupidatat non proident, sunt in culpa qui officia deserunt
									mollit anim id est laborum.</p>
							</div>
							<div class="tab-pane fade" id="help-pills">
								<h4>Описание</h4>
								<p>Окно для работы с xml запросами. Предназначено для работы
									с ЦС ЕРЗ.</p>
								<p>Первый лист предназначен для вставки данных которые
									необходимы для того или иного запроса.</p>
								<p>На второй лист выводится информация которая содержится в
									базе ТФОМС.</p>
								<p>На третий лист выводится информация которая содержится в
									базе ЦС ЕРЗ.</p>
								<p>Четвертый лист является вспомагательным. На него мы
									можете копировать временную информацию и т.п.</p>
							</div>
						</div>
					</div>
					<!-- /.panel-body -->
				</div>
				<!-- /.panel -->
			</div>
			<!-- /.col-lg-6 -->
		</div>



		<div id="dim">
			<div class="msgbox">
				<!-- 	<a class="close" href="#" ><img src="close.jpg"/></a>  -->
				<div class="msgboxp">
					<h4>Произошел обрыв соединения. Для дальнейшей работы обновите
						страницу. Если это не помогло обратитесь к администратору.</h4>
				</div>
			</div>
		</div>
		<!-- блок spinner -->
		<div id="dim2">
			<div id="spinner_center" style="position: fixed; top: 49%; left: 49%;"></div>
		</div>
		
		<div id="dim3">
			<div id ="spinner_center2" style="position:fixed;top:45%;left:49%;"></div>
			<div class="progress" id="testprogressbarrr">
			    <div class="progress-bar" id="testprogressbar">
		    	    0%
		    	</div>
			</div>
		</div>
	</div>
	<!-- conteinerHeader -->
	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->



 <script>
  /*$( function() {
    // run the currently selected effect
    function runEffect() {
      // get effect type from
      var selectedEffect = 'scale';
 
      // Most effect types need no options passed by default
      var options = {};
      // some effects have required parameters
      if ( selectedEffect === "scale" ) {
        options = { percent: 50 };
      } 
 
      // Run the effect
      $( "#tableexcel" ).effect( selectedEffect, options, 500, callback );
    };
 
    // Callback function to bring a hidden box back
    function callback() {
      setTimeout(function() {
        $( "#tableexcel" ).removeAttr( "style" ).hide().fadeIn();
      }, 5000 );
    };
 
    // Set effect from select menu value
    $( "#A03P07" ).on( "click", function() {
      runEffect();
      return false;
    });
  } );*/
  </script>

       
       
       
</body>
</html>
