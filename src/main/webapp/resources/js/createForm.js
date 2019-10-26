$(function() {
  
  if($.cookie('questionsOrder') != null){
    createForm();
  }
  $( "#addQuestion" ).click(function() {
    
    var questionId = generateId(20);
    addQuestion(questionId);

    var questionTypes = ['text', 'paragraph', 'number', 'pulldown', 'radio', 'checkbox', 'date', 'time'];
    var questionTypesNames = ['記述式', '段落', '数字', 'プルダウン', 'ラジオ', 'チェックボックス', '日付', '時間'];
    var questionTypeOptions = '';
    var defaultType = 'text';
    var defaultTypeName = 'テキスト';

    for(var i = 0; i < questionTypes.length; i++){
      if(questionTypes[i] == defaultType)
        questionTypeOptions += "<option value=\""+questionTypes[i]+"\" selected>"+questionTypesNames[i]+"</option>";
      else
        questionTypeOptions += "<option value=\""+questionTypes[i]+"\">"+questionTypesNames[i]+"</option>";
    }

    $.cookie(questionId, defaultTypeName);
    $.cookie(questionId + '-type', defaultType);
    $.cookie(questionId + '-required', true);

    $("#questionField").append("<section id=\""+questionId+"\" class=\"box\">"
      + "<div class=\"row gtr-uniform gtr-50\">"
      + "<div class=\"col-5-5 col-5-mobile\">"
      + "</div>"
      + "<div class=\"col-4 col-3-mobile\">"
      + "<span id=\"sortButton\" class=\"fas fa-sort fa-2x\" style=\"cursor: -webkit-grabbing; cursor: grabbing;\"></span>"
      + "</div>"
      + "<div class=\"col-1-narrower\">"
      + "<h5>必須</h5>"
      + "</div>"
      + "<div class=\"col-1 col-3-mobile\">"
      + "<label class=\"switch\">"
      + "<input type=\"checkbox\" checked onclick=\"if($.cookie('"+questionId+"-required') == 'true'){$.cookie('"+questionId+"-required', false);} else {$.cookie('"+questionId+"-required', true);}\">"
      + "<span class=\"slider round\"></span>"
      + "</label>"
      + "</div>"
      + "</div>"
      + "<div id=\""+questionId+"-container\" class=\"col-12\">"
      + "<h3 contenteditable=\"true\" onblur=\"$.cookie('"+questionId+"', $(this).html())\" onkeyup=\"$.cookie('"+questionId+"', $(this).html())\">質問を入力</h3>"
      + "</div>"
      + "<br />"
      + "<div class=\"row gtr-uniform gtr-50\">"
      + "<div class=\"col-4 col-2-mobile\">"
      + "</div>"
      + "<div class=\"col-4 col-7-mobile\">"
      + "<select onchange=\"additionalOptionsIdentifier('"+questionId+"', $(this).val())\">"
      + questionTypeOptions
      + "</select>"
      + "</div>"
      + "<div class=\"col-2 col-0-mobile\">"
      + "</div>"
      + "<div class=\"col-2 col-3-mobile\">"
      + "<span id=\"deleteQuestion\" class=\"fas fa-trash fa-2x\" style=\"color: #ff7496; cursor: -webkit-grabbing; cursor: grabbing;\" onclick=\"deleteQuestion('"+questionId+"');\"></span>"
      + "</div>"
      + "</div>"
      + "</section>"
    );
  });

  $('#questionField').sortable({
    cursor: 'move',
    handle: '#sortButton',
    update: function(event, ui) {
      var questionsOrder = $('#questionField').sortable('toArray');

      $.cookie("questionsOrder", questionsOrder, { expires: 0.5 });
    }
  });

  $('#questionField').disableSelection();

  setTimeout(function() {
    $("#warning").hide('blind', {}, 500)
  }, 5000);
  
});

function generateId(length) {
  var result           = '';
  var characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  var charactersLength = characters.length;
  for ( var i = 0; i < length; i++ ) {
    result += characters.charAt(Math.floor(Math.random() * charactersLength));
  }
  return result;
}

function addQuestion(questionId){
  var questionsOrder = $.cookie('questionsOrder');

  if($("#questionField").children().length == 0){
    $.cookie('questionsOrder', questionId);
  } else {
    $.cookie('questionsOrder', questionsOrder+","+questionId);
  }
}

function additionalOptionsIdentifier(questionId, questionType){

  $.cookie(questionId+"-type", questionType); 
  
  if(questionType == 'pulldown' || questionType == 'radio' || questionType == 'checkbox') {
    removeDateContainer(questionId);
    removeTimeContainer(questionId);
    addOptionFeature(questionId);
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

  if($("#"+questionId+"-container").children().length > 1)
    return;
  
  var optionsContainer = "<div id=\""+questionId+"-additional-options-container\" class=\"col-12\"></div>"
  + "<div class=\"row gtr-50 gtr-uniform\">"
  + "<div class=\"col-1 col-2-mobile\"></div>"
  + "<div class=\"col-11 col-10-mobile\">"
  + "<span id=\""+questionId+"-addOption\" class=\"fas fa-plus-circle fa-2x mouse-pointer\" onclick=\"addNewOption('"+questionId+"')\"></span>"
  + "</div>"
  + "</div>";

  $("#"+questionId+"-container").append(optionsContainer);

  addNewOption(questionId);

  var sortableOptionsScriptCode = "$(\"#"+questionId+"-additional-options-container\").sortable({"
  + "cursor: 'move',"
  + "handle: '#questionOptionsSortButton',"
  + "update: function(event, ui) {"
  + "var questionOptionsOrder = $(\"#"+questionId+"-additional-options-container\").sortable('toArray');"
  + "$.cookie(\""+questionId+"-options-order\", questionOptionsOrder);"
  + "}"
  + "});"
  + "$(\"#"+questionId+"-additional-options-container\").disableSelection();";

  $('<script>')
  .attr('type', 'text/javascript')
  .text(sortableOptionsScriptCode)
  .appendTo('body');
}

function removeAdditionalOptions(questionId){

  $.removeCookie(questionId+"-options-order");

  $("#"+questionId+"-additional-options-container").remove();
  $("#"+questionId+"-addOption").remove();
}

function removeDateContainer(questionId) {
  $.removeCookie(questionId+'-date-start');
  $.removeCookie(questionId+'-date-end');

  $("#"+questionId+"-date-container").remove();
}

function removeTimeContainer(questionId) {
  $.removeCookie(questionId+'-time-start');
  $.removeCookie(questionId+'-time-end');

  $("#"+questionId+"-time-container").remove();
}

function addNewOption(questionId){

  var optionId = generateId(5);
  $.cookie(optionId, ' ');

  var optionsField = "<div class=\"row gtr-50 gtr-uniform\" id=\""+optionId+"\">"
  + "<div class=\"col-1\">"
  + "<span id=\"questionOptionsSortButton\" class=\"fas fa-sort fa-2x\" style=\"cursor: -webkit-grabbing; cursor: grabbing;\"></span>"
  + "</div>"
  + "<div class=\"col-9\">"
  + "<h3 contenteditable=\"true\" onblur=\"$.cookie('"+optionId+"', $(this).html())\" onkeyup=\"$.cookie('"+optionId+"', $(this).html())\">項目を入力</h3>"
  + "</div>"
  + "<div class=\"col-2\">"
  + "<span class=\"fas fa-times fa-2x\" style=\"cursor: -webkit-grabbing; cursor: grabbing;\" onclick=\"removeOption('"+questionId+"', '"+optionId+"')\"></span>"
  + "</div>"
  + "</div>";

  $("#"+questionId+"-additional-options-container").append(optionsField);
  
  var optionsOrder = $.cookie(questionId+"-options-order");
  if(optionsOrder == null) {
    $.cookie(questionId+"-options-order", optionId);
  } else {
    $.cookie(questionId+"-options-order", optionsOrder + ',' + optionId);
  }
}

function removeOption(questionId, optionId){
  $("#"+optionId).remove();
  $.removeCookie(optionId);

  var optionsOrder = $.cookie(questionId+'-options-order');

  optionsOrder = optionsOrder.split(',');
  var index = optionsOrder.indexOf(optionId);
  optionsOrder.splice(index, 1);

  if(optionsOrder.length > 0){
    $.cookie(questionId+'-options-order', optionsOrder);
  } else {
    $.removeCookie(questionId+'-options-order');
  }
  
  
}

function addDatePeriodFeature(questionId){

  var defaultStartPeriod = "1970-01-01";
  var defaultEndPeriod = "2019-01-08";

  $.cookie(questionId+"-date-start", defaultStartPeriod);
  $.cookie(questionId+"-date-end", defaultEndPeriod);
  
  var datePeriodContainer = "<div id=\""+questionId+"-date-container\" class=\"col-12\">"
  + "<div class=\"row gtr-50 gtr-uniform\">"
  + "<div class=\"col-2 col-4-mobile\">"
  + "<input type=\"date\" id=\""+questionId+"-start-period\" value=\""+defaultStartPeriod+"\" onblur=\"$.cookie('"+questionId+"-date-start', $(this).val()); $('#"+questionId+"-end-period').attr('min', $(this).val())\"/>"
  + "</div>"
  + "<div class=\"col-2\">"
  + "から"
  + "</div>"
  + "<div class=\"col-2 col-4-mobile\">"
  + "<input type=\"date\" id=\""+questionId+"-end-period\" value=\""+defaultEndPeriod+"\" onblur=\"$.cookie('"+questionId+"-date-end', $(this).val()); $('#"+questionId+"-start-period').attr('max', $(this).val())\"/>"
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

  $.cookie(questionId+"-time-start", defaultStartPeriod);
  $.cookie(questionId+"-time-end", defaultEndPeriod);
  
  var timePeriodContainer = "<div id=\""+questionId+"-time-container\" class=\"col-12\">"
  + "<div class=\"row gtr-50 gtr-uniform\">"
  + "<div class=\"col-2 col-4-mobile\">"
  + "<input type=\"time\" id=\""+questionId+"-start-period\" value=\""+defaultStartPeriod+"\" onblur=\"$.cookie('"+questionId+"-time-start', $(this).val()); $('#"+questionId+"-end-period').attr('min', $(this).val())\"/>"
  + "</div>"
  + "<div class=\"col-2\">"
  + "から"
  + "</div>"
  + "<div class=\"col-2 col-4-mobile\">"
  + "<input type=\"time\" id=\""+questionId+"-end-period\" value=\""+defaultEndPeriod+"\" onblur=\"$.cookie('"+questionId+"-time-end', $(this).val()); $('#"+questionId+"-start-period').attr('max', $(this).val())\"/>"
  + "</div>"
  + "<div class=\"col-2\">"
  + "まで"
  + "</div>"
  + "</div>"
  + "</div>";

  $("#"+questionId+"-container").append(timePeriodContainer);
  
}

function deleteQuestion(questionId){
  
  $("#"+questionId).remove();
  $.removeCookie(questionId);
  $.removeCookie(questionId + '-type');
  $.removeCookie(questionId + '-required');
  $.removeCookie(questionId+'-date-start');
  $.removeCookie(questionId+'-date-end');
  $.removeCookie(questionId+'-time-start');
  $.removeCookie(questionId+'-time-end');

  var optionsOrder = $.cookie(questionId+"-options-order");
  if(optionsOrder != null) {
    var optionsOrder = optionsOrder.split(',');
    
    for(var i = 0; i < optionsOrder.length;  i++){
      $.removeCookie(optionsOrder[i]);
    }
  }
  $.removeCookie(questionId+"-options-order");

  var questionsOrder = $.cookie('questionsOrder').split(',');
  var index = questionsOrder.indexOf(questionId);
  
  questionsOrder.splice(index, 1);
  
  if(questionsOrder.length == 0){
    $.removeCookie('questionsOrder');
  } else {
    $.cookie('questionsOrder', questionsOrder);
  }
}