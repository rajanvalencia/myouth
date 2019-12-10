$(function() {

  $('#title').blur(function(){

    var title = "無題のテンプレート";

    if($(this).html().length > 0) {
      title = $(this).html();
    } else {
      $(this).append(title);
    }
    
    ajaxEditTitle(title);
  });

  $('#description').blur(function(){

    var description = "詳細未記入";

    if($(this).html().length > 0) {
      description = $(this).html();
    } else {
      $(this).append(description);
    }

    ajaxEditDescription(description);
  });

  $( "#addQuestion" ).click(function() {
    
    var questionId = generateId(50);
    var question = '質問を入力';
    var questionType = 'text';
    var questionRequired = 'true';
    ajaxAddQuestion(questionId, question, questionType, questionRequired);
    generateQuestion(questionId, question, questionType, questionRequired);
  });

  $('#questionField').sortable({
    cursor: 'move',
    handle: '#sortButton',
    update: function(event, ui) {
      var questionIds = $('#questionField').sortable('toArray');
      var params = {
        templateId : sessionStorage.getItem('templateId'),
        event  : sessionStorage.getItem('event'),
        questionIds : String(questionIds),
        apiKey : sessionStorage.getItem('apiKey')
      }
      
      $.ajax({
        type    : 'POST',
        url     : '/apis/ajax/forms/sortQuestions',
        data    : params,
        async   : true,
        success : function(data) {
          response = data["res"];
        },
        error   : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("リクエスト時になんらかのエラーが発生しました：" + textStatus +": " + errorThrown);
        }
    });
    }
  });

  $('#questionField').disableSelection();

  setTimeout(function() {
    $("#warning").toggle('blind')
  }, 20000);

});

/* Setting the expiration date of cookie*/

function generateId(length) {
  var result           = '';
  var characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  var charactersLength = characters.length;
  for ( var i = 0; i < length; i++ ) {
    result += characters.charAt(Math.floor(Math.random() * charactersLength));
  }
  return result;
}

function additionalOptionsIdentifier(questionId, questionType){

  ajaxEditQuestionType(questionId, questionType);

  if($('#'+questionId+'-date-container').length > 0){
    removeDateContainer(questionId);
  } else if($('#'+questionId+'-time-container').length > 0) {
    removeTimeContainer(questionId);
  }

  if(questionType == 'pulldown' || questionType == 'radio' || questionType == 'checkbox') {
    if($("#"+questionId+"-container").children().length <= 1){
      addOptionFeature(questionId);
    }
  } else if(questionType == 'date') {
    removeAdditionalOptions(questionId);
    removeTimeContainer(questionId);
    addDatePeriodFeature(questionId);
  } else if(questionType == 'time') {
    removeAdditionalOptions(questionId);
    removeDateContainer(questionId);
    addTimePeriodFeature(questionId);
  } else {
    removeAdditionalOptions(questionId);
  }
  
}

function addOptionFeature(questionId){

  var optionsContainer = "<div id=\""+questionId+"-options-container\" class=\"col-12\"></div>"
  + "<div class=\"row gtr-50 gtr-uniform\">"
  + "<div class=\"col-1 col-2-mobile\"></div>"
  + "<div class=\"col-11 col-10-mobile\">"
  + "<span id=\""+questionId+"-addOption\" class=\"fas fa-plus-circle fa-2x mouse-pointer\" onclick=\"addNewOption('"+questionId+"')\"></span>"
  + "</div>"
  + "</div>";

  $("#"+questionId+"-container").append(optionsContainer);

  addNewOption(questionId);

  var sortableOptionsScriptCode = "$('#"+questionId+"-options-container').sortable({"
  + "\n\tcursor: 'move',"
  + "\n\thandle: '#questionOptionsSortButton',"
  + "\n\tupdate: function(event, ui) {"
  + "\n\t\tvar optionIds = $('#"+questionId+"-options-container').sortable('toArray');"
  + "\n\t\tvar params = {"
  + "\n\t\t\ttemplateId : sessionStorage.getItem('templateId'),"
  + "\n\t\t\tevent  : sessionStorage.getItem('event'),"
  + "\n\t\t\tquestionId : '"+questionId+"'," 
  + "\n\t\t\toptionIds : String(optionIds),"
  + "\n\t\t\tapiKey : sessionStorage.getItem('apiKey')"
  + "\n\t\t}"
  + "\n\t\t$.ajax({"
  + "\n\t\t\ttype    : 'POST',"       
  + "\n\t\t\turl     : '/apis/ajax/forms/sortOptions',"
  + "\n\t\t\tdata    : params,"
  + "\n\t\t\tasync   : true,"
  + "\n\t\t\tsuccess : function(data) {"
  + "\n\t\t\t\tresponse = data['res'];"
  + "\n\t\t\t},"
  + "\n\t\t\terror   : function(XMLHttpRequest, textStatus, errorThrown) {"
  + "\n\t\t\t\talert('リクエスト時になんらかのエラーが発生しました：' + textStatus +': ' + errorThrown);"
  + "\n\t\t\t}"
  + "\n\t\t});"
  + "\n\t}"
  + "\n});"
  + "\n$(\"#"+questionId+"-options-container\").disableSelection();";

  $('<script>')
  .attr('type', 'text/javascript')
  .text(sortableOptionsScriptCode)
  .appendTo('body');
}

function removeAdditionalOptions(questionId){
  $("#"+questionId+"-options-container").remove();
  $("#"+questionId+"-addOption").remove();
}

function removeDateContainer(questionId) {
  $("#"+questionId+"-date-container").remove();
}

function removeTimeContainer(questionId) {
  $("#"+questionId+"-time-container").remove();
}

function addNewOption(questionId){

  var optionId = generateId(5);
  ajaxAddOption(questionId, optionId, '項目を入力');

  var optionsField = "<div class=\"row gtr-50 gtr-uniform\" id=\""+optionId+"\">"
  + "<div class=\"col-1\">"
  + "<span id=\"questionOptionsSortButton\" class=\"fas fa-sort fa-2x\" style=\"cursor: -webkit-grabbing; cursor: grabbing;\"></span>"
  + "</div>"
  + "<div class=\"col-9\">"
  + "<h3 id=\""+optionId+"-name\">項目を入力</h3>"
  + "</div>"
  + "<div class=\"col-2\">"
  + "<span class=\"fas fa-times fa-2x\" style=\"cursor: -webkit-grabbing; cursor: grabbing;\" onclick=\"ajaxDeleteOption('"+questionId+"', '"+optionId+"')\"></span>"
  + "</div>"
  + "</div>";

  $("#"+questionId+"-options-container").append(optionsField);

  var optionScriptCode = "$(function(){"
    + "\n\t$('#"+optionId+"-name').click(function(){"
    + "\n\t\t$(this).attr(\"contenteditable\", \"true\");"
    + "\n\t\tplaceCursorAtEnd($(this).get(0));"
    + "\n\t});"
    + "\n"
    + "\n\t$('#"+optionId+"-name').blur(function(){"
    + "\n\t\t$(this).attr(\"contenteditable\", \"false\");"
    + "\n\t\tif($(this).html().length == 0){"
    + "\n\t\t\tajaxEditOption('"+questionId+"', '"+optionId+"', '項目を入力');"
    + "\n\t\t\t$('#"+optionId+"-name').append('項目を入力');"
    + "\n\t\t} else {"
    + "\n\t\t\tajaxEditOption('"+questionId+"', '"+optionId+"', $(this).html());"
    + "\n\t\t}"
    + "\n\t});"
    + "\n"
    + "\n});"
   
    $('<script>')
    .attr('type', 'text/javascript')
    .text(optionScriptCode)
    .appendTo('body');
}


function addDatePeriodFeature(questionId){

  var defaultStartPeriod = "1970-01-01";
  var defaultEndPeriod = "2019-01-08";

  ajaxAddDateOrTimePeriod(questionId, 'date', defaultStartPeriod, defaultEndPeriod);

  var datePeriodContainer = "<div id=\""+questionId+"-date-container\" class=\"col-12\">"
  + "<div class=\"row gtr-50 gtr-uniform\">"
  + "<div class=\"col-2 col-4-mobile\">"
  + "<input type=\"date\" id=\""+questionId+"-start-period\" value=\""+defaultStartPeriod+"\" onblur=\"ajaxEditDateOrTimePeriod('"+questionId+"', 'date', $(this).val(), $('#"+questionId+"-end-period').val()); $('#"+questionId+"-end-period').attr('min', $(this).val());\"/>"
  + "</div>"
  + "<div class=\"col-2\">"
  + "から"
  + "</div>"
  + "<div class=\"col-2 col-4-mobile\">"
  + "<input type=\"date\" id=\""+questionId+"-end-period\" value=\""+defaultEndPeriod+"\" onblur=\"ajaxEditDateOrTimePeriod('"+questionId+"', 'date', $('#"+questionId+"-start-period').val(), $(this).val()); $('#"+questionId+"-start-period').attr('max', $(this).val());\"/>"
  + "</div>"
  + "<div class=\"col-2\">"
  + "まで"
  + "</div>"
  + "</div>"
  + "</div>";

  $("#"+questionId+"-container").append(datePeriodContainer);
  
}

function addTimePeriodFeature(questionId){

  var defaultStartPeriod = "07:00";
  var defaultEndPeriod = "19:00";

  ajaxAddDateOrTimePeriod(questionId, 'time', defaultStartPeriod, defaultEndPeriod);

  var timePeriodContainer = "<div id=\""+questionId+"-time-container\" class=\"col-12\">"
  + "<div class=\"row gtr-50 gtr-uniform\">"
  + "<div class=\"col-2 col-4-mobile\">"
  + "<input type=\"time\" id=\""+questionId+"-start-period\" value=\""+defaultStartPeriod+"\" onblur=\"ajaxEditDateOrTimePeriod('"+questionId+"', 'date', $(this).val(), $('#"+questionId+"-end-period').val()); $('#"+questionId+"-end-period').attr('min', $(this).val());\"/>"
  + "</div>"
  + "<div class=\"col-2\">"
  + "から"
  + "</div>"
  + "<div class=\"col-2 col-4-mobile\">"
  + "<input type=\"time\" id=\""+questionId+"-end-period\" value=\""+defaultEndPeriod+"\" onblur=\"ajaxEditDateOrTimePeriod('"+questionId+"', 'date', $('#"+questionId+"-start-period').val(), $(this).val()); $('#"+questionId+"-start-period').attr('max', $(this).val());\"/>"
  + "</div>"
  + "<div class=\"col-2\">"
  + "まで"
  + "</div>"
  + "</div>"
  + "</div>";

  $("#"+questionId+"-container").append(timePeriodContainer);
  
}
