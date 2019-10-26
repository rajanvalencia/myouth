function checkForm() {
  var questionsOrder = $.cookie('questionsOrder');
  if(questionsOrder != null){
    $('#questionField').hide();
    $('#addQuestionField').hide();
    $('#createFormIcons').hide();
    $('#checkQuestionField').removeClass('hidden');
    $('#checkQuestionContainer').removeClass('hidden');
    $('#checkFormIcons').removeClass('hidden');
    $('#questionField').html('');
    $('#checkQuestionField').html('');
    $('#title').attr('contenteditable', false);
    $('#description').attr('contenteditable', false);

    var defaultQuestions = "<div class=\"col-12\">"
	  + "<label for=\"name\">氏名'<span class=\"fa fa-asterisk\" style=\"color: #ff7496;\"></span></label>"
	  + "<input type=\"text\" name=\"name\" value=\"\" />"
	  + "</div>"
    + "<div class=\"col-12\">"
	  + "<label for=\"fname\">フリガナ'<span class=\"fa fa-asterisk\" style=\"color: #ff7496;\"></span></label>"
	  + "<input type=\"text\" name=\"fname\" value=\"\" />"
	  + "</div>"
	  + "<div class=\"col-12\">"
	  + "<label for=\"email-address\">メールアドレス'<span class=\"fa fa-asterisk\" style=\"color: #ff7496;\"></span></label>"
	  + "<input type=\"email\" name=\"email-address\" value=\"\" />"
	  + "</div>";
    $('#checkQuestionField').append(defaultQuestions);

    questionsOrder = questionsOrder.split(',');
    for(var i = 0; i < questionsOrder.length; i++){
      visualizeQuestion(questionsOrder[i]);
    }
  }
}

function visualizeQuestion(questionId){
  var questionHtml = "<div class=\""+getWidth(questionId)+"\"><label for=\""+questionId+"\"><h4>"+getQuestion(questionId)+"</h4></label>"+getInputHtml(questionId)+"</div>";

  if(getType(questionId) == 'date' || getType(questionId) == 'time'){
    questionHtml += "<div class=\"col-9 col-8-mobile\"></div>";
  }

  $('#checkQuestionField').append(questionHtml);
}

function getQuestion(questionId){
  return $.cookie(questionId)+" "+getRequiredHtml(questionId);
}

function getInputHtml(questionId){
  var inputHtml = '';

  if(getType(questionId) == 'pulldown') {
    var optionsId = $.cookie(questionId+'-options-order').split(',');

    inputHtml += "<select id=\""+questionId+"\" name=\""+questionId+"\" "+getRequireOption(questionId)+">";

    for(var i = 0; i < optionsId.length; i++){
      inputHtml += "<option value=\""+optionsId[i]+"\">"+$.cookie(optionsId[i])+"</option>";
    }

    inputHtml += "</select>";

  } else if(getType(questionId) == 'radio') {
    var optionsId = $.cookie(questionId+'-options-order').split(',');
    
    for(var i = 0; i < optionsId.length; i++){
      inputHtml += "<div class=\"col-12\">"
      +"<input type=\""+getType(questionId)+"\" id=\""+optionsId[i]+"\" name=\""+questionId+"\"><label for=\""+optionsId[i]+"\">"+$.cookie(optionsId[i])+"</label>"
      +"</div>";
    }

  } else if(getType(questionId) == 'checkbox') {
    var optionsId = $.cookie(questionId+'-options-order').split(',');
    
    for(var i = 0; i < optionsId.length; i++){
      inputHtml += "<div class=\"col-12\">"
      +"<input type=\""+getType(questionId)+"\" id=\""+optionsId[i]+"\" name=\""+optionsId[i]+"\"><label for=\""+optionsId[i]+"\">"+$.cookie(optionsId[i])+"</label>"
      +"</div>";
    }

  } else if(getType(questionId) == 'paragraph') {
    
    inputHtml += "<textarea id=\""+questionId+"\" name=\""+questionId+"\" placeholder=\"\" rows=\"4\" "+getRequireOption(questionId)+"></textarea>"; 

  } else if(getType(questionId) == 'date') {

    var startPeriod = $.cookie(questionId+"-date-start");
    var endPeriod = $.cookie(questionId+"-date-end");

    inputHtml += "<input type=\"date\" id=\""+questionId+"\" name=\""+questionId+"\" min=\""+startPeriod+"\" max=\""+endPeriod+"\" value=\"\" "+getRequireOption(questionId)+"/>";
    
  } else if(getType(questionId) == 'time') {

    var startPeriod = $.cookie(questionId+"-time-start");
    var endPeriod = $.cookie(questionId+"-time-end");

    inputHtml += "<input type=\"time\" id=\""+questionId+"\" name=\""+questionId+"\" min=\""+startPeriod+"\" max=\""+endPeriod+"\" value=\"\" "+getRequireOption(questionId)+"/>";
    
  } else {
    inputHtml += "<input id=\""+questionId+"\" type=\""+getType(questionId)+"\" name=\""+questionId+"\" value=\"\" "+getRequireOption(questionId)+"/>";
  }
  return inputHtml;
}

function getWidth(questionId) {
  if(getType(questionId) == 'date' || getType(questionId) == 'time') {
    return 'col-3 col-4-mobile';
  } else {
    return 'col-12';
  }
}

function getType(questionId) {
  return $.cookie(questionId+'-type');
}

function getRequiredHtml(questionId){
  if($.cookie(questionId+'-required') == 'true') {
    return '<span class=\"fa fa-asterisk\" style=\"color: #ff7496;\"></span>';
  } else {
    return '';
  }
}

function getRequireOption(questionId) {
  if($.cookie(questionId+'-required') == 'true'){
    return 'required';
  } else {
    return '';
  }
}

