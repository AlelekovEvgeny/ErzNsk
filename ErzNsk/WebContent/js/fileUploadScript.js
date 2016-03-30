$(document).ready(function() {
var options = {
	beforeSend : function() {
		$("#progressbox").show();
		// clear everything
		$("#progressbar").width('0%');
		$("#message").empty();
		$("#percent").html("0%");
		if($('#myfile').val() == '')
		{
			
			throw {}; 
		}
		
	},
	uploadProgress : function(event, position, total, percentComplete) {
		$("#progressbar").width(percentComplete + '%');
		$("#percent").html(percentComplete + '%');

		// change message text and % to red after 50%
		if (percentComplete > 50) {
			$("#message").html("<font color='red'>File Upload is in progress .. </font>");
		}
	},
	success : function() {
		$("#progressbar").width('100%');
		$("#percent").html('100%');
	},
	complete : function(response) {
		$('#btnprocessreporterrorgz').prop('disabled',false);
		$('#btnstarttask').prop('disabled', true);
		$('#messageupload').css('display','block');
		$("#messageupload").html("<font color='blue'>Задание загружено успешно. Для начала обработки ошибок нажмите <button type='button' id='btnprocessreporterrorgz'  onclick=ProcessReportErrorGZ() class='btn btn-primary btn-sm'>Обработка</button></font>");
		
	},
	error : function() {
		$("#messageupload").html("<font color='red'> Ошибка загрузки задания. Обновите старницу и повторите снова либо обратитесь в поддержку.</font>");
	}
};
$("#UploadForm").ajaxForm(options);
});

function ProcessReportErrorGZ() 
{  
	$('#progressboxGZ').css('display','block');
	$('#btnprocessreporterrorgz').prop('disabled', true);
	$('#myfile').prop('disabled', true);
	var shtrout = '';
	$.ajax({
	        url: "processerror",
	        type: 'GET',
	        dataType: 'json',
	        //data: '',
	        contentType: 'application/json',
	        success: function (data) {
	        	console.log('s '+data);
	        },
	        error: function (err) {
	        	//console.log('err '+JSON.stringify(err));
	        	shtrout = err.responseText;
	        },
	        complete: function(jqXHR, textStatus)
	        {}    
		});
	/*
	  При нажатии обработка ошибок гознака делать запрос на наличие количества заданий ,если другим способом то затирается эксель
	*/
	setTimeout(function(){/*console.log(shtrout != 'stop'); */ if(shtrout != 'stop'){ checkProgressErrorGZ();}else{
		$('#messageupload').css('display','block');
		$("#messageupload").html("<font color='red'><i class='fa fa-warning faa-flash animated-hover'></i> Ошибка. Задание ранее загружено и работает. Обратитесь к поддержке.</font>");
		$('#myfile').prop('disabled', false);
	}}, 5000);          
};

function checkProgressErrorGZ() {
	
	$('#progressboxGZ').css('display','block');
    $.ajax({
        url: "ProgressBar",
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        success: function ( dat)
         {
        	//console.log('test '+dat);
        	$("#progressbarprocessGZ").width(dat + '%');
    		$("#percentprocessGZ").html(dat + '%');
    		
        	if (parseInt(dat) < 100) {
	            setTimeout(checkProgressErrorGZ, 12000); // Checks again after one second.
        	}
        	else
        	{
        		$('#messageend').css('display','block');
        		$('#myfile').prop('disabled', false);
        		$("#messageend").html("<font color='blue'>Обработка ошибок завершена. Чтобы получить отчет нажмите <button type='button' class='btn btn-success btn-sm' onclick=downloadProcessReportErrorGZ()>скачать</button></font>");
        	}
         },
            complete: function(jqXHR, textStatus){}    
    });
}

function downloadProcessReportErrorGZ() 
{  
    document.location.href = '/ErzNsk/reporterrorgz'
};


function myfilefun()
        {  
				$('#btnstarttask').prop('disabled', false);
				$("#messageend").css('display','none');
				$("#progressbarprocessGZ").width('0%');
	    		$("#percentprocessGZ").html('0%');
				$('#progressboxGZ').css('display','none');
				$("#progressbar").width('0%');
				$("#percent").html('0%');
				$('#messageupload').css('display','none');
        };