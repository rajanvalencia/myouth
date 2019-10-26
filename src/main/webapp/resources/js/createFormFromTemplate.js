function createForm(){

  $('#questionField').show();
  $('#addQuestionField').show();
  $('#createFormIcons').show();
  $('#checkQuestionContainer').addClass('hidden');
  $('#checkQuestionField').addClass('hidden');
  $('#checkFormIcons').addClass('hidden');
  $('#title').attr('contenteditable', true);
  $('#description').attr('contenteditable', true);

  

  var questionsOrder = $.cookie('questionsOrder');

  if(questionsOrder != null){
    questionsOrder = questionsOrder.split(',');
    for(var i = 0; i < questionsOrder.length; i++){
      generateQuestion(questionsOrder[i]);
    }
  }
}

function generateQuestion(questionId){
  var question = $.cookie(questionId);
 
  var questionType = $.cookie(questionId+'-type');
  var optionTypeHtml = generateQuestionTypeHtml(questionId);  
  var questionRequired =  generateQuestionRequiredHtml(questionId);
  
  appendQuestionHtml(question, questionId, questionRequired, optionTypeHtml);

  if(questionType == 'pulldown' || questionType == 'radio' || questionType == 'checkbox'){
    addOptionHtml(questionId);
  } else if(questionType == 'date') {
    addDatePeriodHtml(questionId);
  } else if(questionType == 'time') {
    addTimePeriodHtml(questionId);
  }
}

function generateQuestionRequiredHtml(questionId){
  var questionRequired = $.cookie(questionId+'-required');
  if(questionRequired == 'true'){
    questionRequired = 'checked';
  } else {
    questionRequired = '';
  }

  return questionRequired;
}

function generateQuestionTypeHtml(questionId){
  var questionType = $.cookie(questionId+'-type');
  
  var questionTypes = ['text', 'paragraph', 'number', 'pulldown', 'radio', 'checkbox', 'date', 'time'];
  var questionTypesNames = ['記述式', '段落', '数字', 'プルダウン', 'ラジオ', 'チェックボックス', '日付', '時間'];

  var questionTypeOptions = '';
  for(var i = 0; i < questionTypes.length; i++){
    if(questionTypes[i] == questionType)
      questionTypeOptions += "<option value=\""+questionTypes[i]+"\" selected>"+questionTypesNames[i]+"</option>";
    else
      questionTypeOptions += "<option value=\""+questionTypes[i]+"\">"+questionTypesNames[i]+"</option>";
  }
  return questionTypeOptions;
}

function addOptionHtml(questionId){
  
  var optionsContainer = "<div id=\""+questionId+"-options-container\" class=\"col-12\"></div>"
  + "<div class=\"row gtr-50 gtr-uniform\">"
  + "<div class=\"col-1 col-2-mobile\"></div>"
  + "<div class=\"col-11 col-10-mobile\">"
  + "<span id=\""+questionId+"-addOption\" class=\"fas fa-plus-circle fa-2x mouse-pointer\" onclick=\"addNewOption('"+questionId+"')\"></span>"
  + "</div>"
  + "</div>";

  $("#"+questionId+"-container").append(optionsContainer);

  var sortableOptionsScriptCode = "$(\"#"+questionId+"-options-container\").sortable({"
  + "cursor: 'move',"
  + "handle: '#questionOptionsSortButton',"
  + "update: function(event, ui) {"
  + "var questionOptionsOrder = $(\"#"+questionId+"-options-container\").sortable('toArray');"
  + "$.cookie(\""+questionId+"-options-order\", questionOptionsOrder);"
  + "}"
  + "});"
  + "$(\"#"+questionId+"-options-container\").disableSelection();";

  $('<script>')
  .attr('type', 'text/javascript')
  .text(sortableOptionsScriptCode)
  .appendTo('body');

  var optionsId = $.cookie(questionId+'-options-order').split(',');

  for(var i = 0; i < optionsId.length; i++){
    var optionsField = "<div class=\"row gtr-50 gtr-uniform\" id=\""+optionsId[i]+"\">"
    + "<div class=\"col-1\">"
    + "<span id=\"questionOptionsSortButton\" class=\"fas fa-sort fa-2x\" style=\"cursor: -webkit-grabbing; cursor: grabbing;\"></span>"
    + "</div>"
    + "<div class=\"col-9\">"
    + "<h3 contenteditable=\"true\" onkeyup=\"$.cookie('"+optionsId[i]+"', $(this).html())\">"+$.cookie(optionsId[i])+"</h3>"
    + "</div>"
    + "<div class=\"col-2\">"
    + "<span class=\"fas fa-times fa-2x\" style=\"cursor: -webkit-grabbing; cursor: grabbing;\" onclick=\"removeOption('"+questionId+"', '"+optionsId[i]+"')\"></span>"
    + "</div>"
    + "</div>";

    $("#"+questionId+"-options-container").append(optionsField);
  }
}

function appendQuestionHtml(question, questionId, questionRequired, questionTypeOptions){
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
      + "<input type=\"checkbox\" "+questionRequired+" onclick=\"if($.cookie('"+questionId+"-required') == 'true'){$.cookie('"+questionId+"-required', false);} else {$.cookie('"+questionId+"-required', true);}\">"
      + "<span class=\"slider round\"></span>"
      + "</label>"
      + "</div>"
      + "</div>"
      + "<div id=\""+questionId+"-container\" class=\"col-12\">"
      + "<h3 contenteditable=\"true\" onkeyup=\"$.cookie('"+questionId+"', $(this).html())\">"+question+"</h3>"
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
}

function addDatePeriodHtml(questionId){

  var defaultStartPeriod = $.cookie(questionId+"-date-start");
  var defaultEndPeriod = $.cookie(questionId+"-date-end");
  
  var datePeriodContainer = "<div id=\""+questionId+"-additional-options-container\" class=\"col-12\">"
  + "<div class=\"row gtr-50 gtr-uniform\">"
  + "<div class=\"col-2 col-4-mobile\">"
  + "<input type=\"date\" id=\""+questionId+"-start-period\" value=\""+defaultStartPeriod+"\" max=\""+defaultEndPeriod+"\" onblur=\"$.cookie('"+questionId+"-date-start', $(this).val()); $('#"+questionId+"-end-period').attr('min', $(this).val())\"/>"
  + "</div>"
  + "<div class=\"col-2\">"
  + "から"
  + "</div>"
  + "<div class=\"col-2 col-4-mobile\">"
  + "<input type=\"date\" id=\""+questionId+"-end-period\" value=\""+defaultEndPeriod+"\" min=\""+defaultStartPeriod+"\" onblur=\"$.cookie('"+questionId+"-date-end', $(this).val()); $('#"+questionId+"-start-period').attr('max', $(this).val())\"/>"
  + "</div>"
  + "<div class=\"col-2\">"
  + "まで"
  + "</div>"
  + "</div>"
  + "</div>";

  $("#"+questionId+"-container").append(datePeriodContainer);
  
}

function addTimePeriodHtml(questionId){

  var defaultStartPeriod = $.cookie(questionId+"-time-start");
  var defaultEndPeriod = $.cookie(questionId+"-time-end");
  
  var datePeriodContainer = "<div id=\""+questionId+"-additional-options-container\" class=\"col-12\">"
  + "<div class=\"row gtr-50 gtr-uniform\">"
  + "<div class=\"col-2 col-4-mobile\">"
  + "<input type=\"time\" id=\""+questionId+"-start-period\" value=\""+defaultStartPeriod+"\" max=\""+defaultEndPeriod+"\" onblur=\"$.cookie('"+questionId+"-time-start', $(this).val()); $('#"+questionId+"-end-period').attr('min', $(this).val())\"/>"
  + "</div>"
  + "<div class=\"col-2\">"
  + "から"
  + "</div>"
  + "<div class=\"col-2 col-4-mobile\">"
  + "<input type=\"time\" id=\""+questionId+"-end-period\" value=\""+defaultEndPeriod+"\" min=\""+defaultStartPeriod+"\" onblur=\"$.cookie('"+questionId+"-time-end', $(this).val()); $('#"+questionId+"-start-period').attr('max', $(this).val())\"/>"
  + "</div>"
  + "<div class=\"col-2\">"
  + "まで"
  + "</div>"
  + "</div>"
  + "</div>";

  $("#"+questionId+"-container").append(datePeriodContainer);
  
}