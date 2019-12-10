function createForm(questionsOrder){

  $('#questionField').show();
  $('#addQuestionField').show();
  $('#createFormIcons').show();
  $('#checkQuestionContainer').addClass('hidden');
  $('#checkQuestionField').addClass('hidden');
  $('#checkFormIcons').addClass('hidden');
  $('#title').attr('contenteditable', true);
  $('#description').attr('contenteditable', true);

  questionsOrder = questionsOrder.split(',');
  for(var i = 0; i < questionsOrder.length; i++){
    generateQuestion(questionsOrder[i]);
  }
}

function generateQuestion(questionId, question, questionType, questionRequired){
 
  var optionTypeHtml = generateQuestionTypeHtml(questionType);  
  var questionRequiredHtml =  generateQuestionRequiredHtml(questionRequired);
  
  appendQuestionHtml(question, questionId, questionRequiredHtml, optionTypeHtml);
}

function generateQuestionRequiredHtml(questionRequired){
  if(questionRequired == 'true'){
    questionRequired = 'checked';
  } else {
    questionRequired = '';
  }

  return questionRequired;
}

function generateQuestionTypeHtml(questionType){
  
  var questionTypeOptions = '';
  for(var i = 0; i < questionTypes.length; i++){
    if(questionTypes[i] == questionType)
      questionTypeOptions += "<option value=\""+questionTypes[i]+"\" selected>"+questionTypesNames[i]+"</option>";
    else
      questionTypeOptions += "<option value=\""+questionTypes[i]+"\">"+questionTypesNames[i]+"</option>";
  }
  return questionTypeOptions;
}

function addOptionHtml(questionId, optionIds, options){
  
  var optionsContainer = "<div id=\""+questionId+"-options-container\" class=\"col-12\"></div>"
  + "<div class=\"row gtr-50 gtr-uniform\">"
  + "<div class=\"col-1 col-2-mobile\"></div>"
  + "<div class=\"col-11 col-10-mobile\">"
  + "<span id=\""+questionId+"-addOption\" class=\"fas fa-plus-circle fa-2x mouse-pointer\" onclick=\"addNewOption('"+questionId+"')\"></span>"
  + "</div>"
  + "</div>";

  $("#"+questionId+"-container").append(optionsContainer);

  var sortableOptionsScriptCode = "$('#"+questionId+"-options-container').sortable({"
  + "\n\tcursor: 'move',"
  + "\n\thandle: '#questionOptionsSortButton',"
  + "\n\tupdate: function(event, ui) {"
  + "\n\t\tvar optionIds = $('#"+questionId+"-options-container').sortable('toArray');"
  + "\n\t\tvar params = {"
  + "\n\t\t\ttemplateId : sessionStorage.getItem('templateId'),"
  + "\n\t\t\tevent  : sessionStorage.getItem('event'), "
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

  for(var i = 0; i < optionIds.length; i++){
    var optionsField = "<div class=\"row gtr-50 gtr-uniform\" id=\""+optionIds[i]+"\">"
    + "<div class=\"col-1\">"
    + "<span id=\"questionOptionsSortButton\" class=\"fas fa-sort fa-2x\" style=\"cursor: -webkit-grabbing; cursor: grabbing;\"></span>"
    + "</div>"
    + "<div class=\"col-9\">"
    + "<h3 id=\""+optionIds[i]+"-name\">"+options[i]+"</h3>"
    + "</div>"
    + "<div class=\"col-2\">"
    + "<span class=\"fas fa-times fa-2x\" style=\"cursor: -webkit-grabbing; cursor: grabbing;\" onclick=\"ajaxDeleteOption('"+questionId+"', '"+optionIds[i]+"')\"></span>"
    + "</div>"
    + "</div>";

    $("#"+questionId+"-options-container").append(optionsField);

    var optionScriptCode = "$(function(){"
    + "\n\t$('#"+optionIds[i]+"-name').click(function(){"
    + "\n\t\t$(this).attr(\"contenteditable\", \"true\");"
    + "\n\t\tplaceCursorAtEnd($(this).get(0));"
    + "\n\t});"
    + "\n"
    + "\n\t$('#"+optionIds[i]+"-name').blur(function(){"
    + "\n\t\t$(this).attr(\"contenteditable\", \"false\");"
    + "\n\t\tif($(this).html().length == 0){"
    + "\n\t\t\tajaxEditOption('"+questionId+"', '"+optionIds[i]+"', '項目を入力');"
    + "\n\t\t\t$('#"+optionIds[i]+"-name').append('項目を入力');"
    + "\n\t\t} else {"
    + "\n\t\t\tajaxEditOption('"+questionId+"', '"+optionIds[i]+"', $(this).html());"
    + "\n\t\t}"
    + "\n\t});"
    + "\n"
    + "\n});"
   
    $('<script>')
    .attr('type', 'text/javascript')
    .text(optionScriptCode)
    .appendTo('body');
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
      + "<input id=\""+questionId+"-requiredOption\" type=\"checkbox\" "+questionRequired+">"
      + "<span class=\"slider round\"></span>"
      + "</label>"
      + "</div>"
      + "</div>"
      + "<h3 id=\""+questionId+"-question\">"+question+"</h3>"
      + "<div id=\""+questionId+"-container\" class=\"col-12\">"
      + "</div>"
      + "<br />"
      + "<div class=\"row gtr-uniform gtr-50\">"
      + "<div class=\"col-4 col-2-mobile\">"
      + "</div>"
      + "<div class=\"col-4 col-7-mobile\">"
      + "<select id=\""+questionId+"-questionType\">"
      + questionTypeOptions
      + "</select>"
      + "</div>"
      + "<div class=\"col-2 col-0-mobile\">"
      + "</div>"
      + "<div class=\"col-2 col-3-mobile\">"
      + "<span id=\""+questionId+"-delete\" class=\"fas fa-trash fa-2x\" style=\"color: #ff7496; cursor: -webkit-grabbing; cursor: grabbing;\"></span>"
      + "</div>"
      + "</div>"
      + "</section>"
    );

    var questionScriptCode = "$(function(){"
    + "\n\t$('#"+questionId+"-question').click(function(){"
    + "\n\t\t$(this).attr(\"contenteditable\", \"true\");"
    + "\n\t\tplaceCursorAtEnd($(this).get(0));"
    + "\n\t});"
    + "\n"
    + "\n\t$('#"+questionId+"-question').blur(function(){"
    + "\n\t\t$(this).attr(\"contenteditable\", \"false\");"
    + "\n\t\tif($(this).html().length == 0){"
    + "\n\t\t\tajaxEditQuestion('"+questionId+"', '質問を入力');"
    + "\n\t\t\t$('#"+questionId+"-question').append('質問を入力');"
    + "\n\t\t} else {"
    + "\n\t\t\tajaxEditQuestion('"+questionId+"', $(this).html());"
    + "\n\t\t}"
    + "\n\t});"
    + "\n"
    + "\n\t$('#"+questionId+"-requiredOption').click(function(){"
    + "\n\t\tvar attr = $(this).attr('checked');"
    + "\n\t\tif (typeof attr !== typeof undefined && attr !== false) {"
    + "\n\t\t\tajaxEditRequiredOption('"+questionId+"', false);"
    + "\n\t\t\t$(this).attr('checked', false);"
    + "\n\t\t} else {"
    + "\n\t\t\tajaxEditRequiredOption('"+questionId+"', true);"
    + "\n\t\t\t$(this).attr('checked', true);"
    + "\n\t\t}"
    + "\n\t});"
    + "\n"
    + "\n\t$('#"+questionId+"-questionType').change(function(){"
    + "\n\t\tadditionalOptionsIdentifier('"+questionId+"', $(this).val());"
    + "\n\t});"
    + "\n"
    + "\n\t$('#"+questionId+"-delete').click(function(){"
    + "\n\t\tajaxDeleteQuestion('"+questionId+"');"
    + "\n\t});"
    + "\n});"
    
    $('<script>')
    .attr('type', 'text/javascript')
    .text(questionScriptCode)
    .appendTo('body');
}

function addDatePeriodHtml(questionId, start, end){

  var datePeriodContainer = "<div id=\""+questionId+"-date-container\" class=\"col-12\">"
  + "<div class=\"row gtr-50 gtr-uniform\">"
  + "<div class=\"col-2 col-4-mobile\">"
  + "<input type=\"date\" id=\""+questionId+"-start-period\" value=\""+start+"\" max=\""+end+"\" onblur=\"$.cookie('"+questionId+"-date-start', $(this).val(), {path: '/', expires: date}); $('#"+questionId+"-end-period').attr('min', $(this).val())\"/>"
  + "</div>"
  + "<div class=\"col-2\">"
  + "から"
  + "</div>"
  + "<div class=\"col-2 col-4-mobile\">"
  + "<input type=\"date\" id=\""+questionId+"-end-period\" value=\""+end+"\" min=\""+start+"\" onblur=\"$.cookie('"+questionId+"-date-end', $(this).val(), {path: '/', expires: date}); $('#"+questionId+"-start-period').attr('max', $(this).val())\"/>"
  + "</div>"
  + "<div class=\"col-2\">"
  + "まで"
  + "</div>"
  + "</div>"
  + "</div>";

  $("#"+questionId+"-container").append(datePeriodContainer);
  
}

function addTimePeriodHtml(questionId, start, end){

  var datePeriodContainer = "<div id=\""+questionId+"-time-container\" class=\"col-12\">"
  + "<div class=\"row gtr-50 gtr-uniform\">"
  + "<div class=\"col-2 col-4-mobile\">"
  + "<input type=\"time\" id=\""+questionId+"-start-period\" value=\""+start+"\" max=\""+end+"\" onblur=\"$.cookie('"+questionId+"-time-start', $(this).val(), {path: '/', expires: date}); $('#"+questionId+"-end-period').attr('min', $(this).val())\"/>"
  + "</div>"
  + "<div class=\"col-2\">"
  + "から"
  + "</div>"
  + "<div class=\"col-2 col-4-mobile\">"
  + "<input type=\"time\" id=\""+questionId+"-end-period\" value=\""+end+"\" min=\""+start+"\" onblur=\"$.cookie('"+questionId+"-time-end', $(this).val(), {path: '/', expires: date}); $('#"+questionId+"-start-period').attr('max', $(this).val())\"/>"
  + "</div>"
  + "<div class=\"col-2\">"
  + "まで"
  + "</div>"
  + "</div>"
  + "</div>";

  $("#"+questionId+"-container").append(datePeriodContainer);
  
}

function placeCursorAtEnd(el) {
  el.focus();
  if (typeof window.getSelection != "undefined"
          && typeof document.createRange != "undefined") {
      var range = document.createRange();
      range.selectNodeContents(el);
      range.collapse(false);
      var sel = window.getSelection();
      sel.removeAllRanges();
      sel.addRange(range);
  } else if (typeof document.body.createTextRange != "undefined") {
      var textRange = document.body.createTextRange();
      textRange.moveToElementText(el);
      textRange.collapse(false);
      textRange.select();
  }
}