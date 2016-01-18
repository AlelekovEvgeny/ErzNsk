    $(document).ready(function()
       { 
    	
    	   var hot1,hot2,hot3;   			 	
			
    	   var data = [[ 'ENP',' ',' ', 'SMO','D_12','D_13','OKATO','TYPE_POL','POL','PFR_SNILS','PFR_ID','PFR_NOTID','USER_SERDOC','USER_NUMDOC','USER_DOCID','USER_DOC_DATE'],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' ']
    	   ];

    	             var container = document.getElementById('list1onsc');
    	              hot1 = new Handsontable(container, {
    	               data: data,
    	              	// Handsontable.helper.createSpreadsheetData(200, 20),
    	               minSpareRows: 1,
    	               rowHeaders: true,
    	               colHeaders: true,
    	               contextMenu: true,
    	               //stretchH: 'all',
    	              //width: 1000,
    	               //height: 500,
    	               //colWidths: 47,
    	               manualColumnResize: true,
    	               manualRowResize: true,
    	               colWidths: [150,	70,	70,	70,	70,	70,	70,	70,	70,	70,	70,	70,	70,	70,	70,	70,	70,	70,	70,	70]
    	              // rowHeights: [50, 40, 100],
    	             });
    	   
           
   
    	   
    	   			 	
			
    	   var data2 = [[ 'PERSON_SERDOC','PERSON_NUMDOC','PERSON_DOCPERSONID','PERSON_SURNAME','PERSON_KINDFIRSTNAME','PERSON_KINDLASTNAME','PERSON_BIRTHDAY','PERSON_SEX	PERSON_LINKSMOESTABLISHMENTID','ENP	PERSON_ADDRESSID','PERSON_DATEINPUT','SNILS','BORN','DATEPASSPORT','ENP_PA','VS_NUM','VS_DATE','ZAD','D2','SMO','D_12','D_13','OKATO_3','TYPE_POL','POL','ENP_1','ENP_2','P14CX1','P14CX5','P14CX6','P14CX7','XPN1','XPN2','XPN3','USERNAME','ZADMINUS1','ZADPLUS40','NBLANC','VS_DATEPLUS1','USER_ENP','USER_PERSON_SURNAME','USER_PERSON_KINDFIRSTNAME','USER_PERSON_KINDLASTNAME','USER_SMO','USER_D_12','USER_D_13','USER_OKATO_3','USER_TYPE_POL','USER_POL','NVL((SELECTALFA3FROMDEVELOPER.OKSMOWHEREO.KOD=RUSSIANANDROWNUM=1),RUS)','D_V','D_SER','D_NUM','PR_FAM','PR_IM','PR_OT','LAST_FAM','LAST_IM','LAST_OT','LAST_DR	PFR_SNILS','PFR_ID','PFR_NOTID','USER_SERDOC','USER_NUMDOC','USER_DOCID','USER_DOC_DATE','D_12_PLUS1'],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' ']
    	   ];

    	             var container2 = document.getElementById('list2onsc');
    	               hot2 = new Handsontable(container2, {
    	               data: data2,
    	              	// Handsontable.helper.createSpreadsheetData(200, 20),
    	               minSpareRows: 1,
    	               rowHeaders: true,
    	               colHeaders: true,
    	               contextMenu: true,
    	              // stretchH: 'all',
    	               //width: 1000,
    	               //height: 500,
    	               //colWidths: 47,
    	               manualColumnResize: true,
    	               manualRowResize: true,
    	               colWidths: [150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150,	150
]
    	              // rowHeights: [50, 40, 100],
    	             });




    	             // Третий лист 
    	             
    	             
    	             
    	             
    	              var data3 = [['ENP','PERSON_SURNAME','PERSON_KINDFIRSTNAME','PERSON_KINDLASTNAME','TRUNC(P.PERSON_BIRTHDAY)','GD','ENP_1','ENP_2','OKATO_2','SMO','D_12','D_13','OKATO_3','TYPE_POL','POL','QRI_1','QRI_2','QRI_3','QRI_4','NPP','D_INPUT','PID7','PID8','PID29'],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' '],
    	               [ ' ', ' ',' ' ,' ']
    	   ];

    	             var container3 = document.getElementById('list3onsc');
    	               hot3 = new Handsontable(container3, {
    	               data: data3,
    	              	// Handsontable.helper.createSpreadsheetData(200, 20),
    	               minSpareRows: 1,
    	               rowHeaders: true,
    	               colHeaders: true,
    	               contextMenu: true,
    	               //stretchH: 'all',
    	               //width: 1500,
    	               //height: 500,
    	               //colWidths: 47,
    	               manualColumnResize: true,
    	               manualRowResize: true,
    	               colWidths: [150, 100, 100, 100, 80, 230, 150,150,30,130,80,80,50,50,30,30,30,30,30,50,100,100,]
    	              // rowHeights: [50, 40, 100],
    	             });
    	             
    	             /*
                     вся та беда подгружается потом hide 
                     */
                     
    	             $('.panel#tableexcel').animate({ 'height': 'hide'}, 0);  
					 /*
					 	Кнопка Таблица
					 	При нажатии заполняем таблицу на странице
					 */
    	             $('#onofftable').click(function (event) {

    	          	  
    	     	        if ($('.panel#tableexcel').is(':visible')) {
    	     	                $('.panel#tableexcel').animate({ 'height': 'hide' }, 2000);

    	     	            	// экспорт с handsontable в excel
    		    	            	 		var e =hot1.countRows()-hot1.countEmptyRows(true);
    		    	            	 		var wd = hot1.countCols()-hot1.countEmptyCols(true);
    		    	            	 		console.log('list1row '+e+' list1col '+ wd);
    		    	            	 		
    		    	            	 		var myData = { list1:hot1.getData(0,0,e-1,wd-1)  } 
    		    	      	                $.post('ExportToExcelFromEmdedTable',JSON.stringify(myData),function(res)
    		    	      	                {
    		    	      	                
    		        	      	            },'json');
    				  	                 
    	     	        } else
    	         	    {	$('.panel#tableexcel').animate({ 'height': 'show'}, 2000);

    	         	   		
    	              		var gouser = "user=" + '<%=user%>';
    	         	    	
	    	         	   var jqxhr = $.getJSON( "ImportFromExcelToHandsontable",gouser, function(data)
	    	    	        {
		            	    	 hot1.loadData(data.data1);
		            	    	 hot2.loadData(data.data2);
		            	    	 hot3.loadData(data.data3);
		            	    })
		            	    	  .done(function() { })
		            	    	  .fail(function() { })
		            	    	  .always(function() { });
    	         	    };
    	            }); 


    	            
        	                
       	});  