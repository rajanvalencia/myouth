function checkForm() {
  
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
	  + "<label for=\"name\">氏名 <span class=\"fa fa-asterisk\" style=\"color: #ff7496;\"></span></label>"
	  + "<input type=\"text\" id=\"name\" name=\"name\" value=\"\" required/>"
	  + "</div>"
    + "<div class=\"col-12\">"
	  + "<label for=\"fname\">フリガナ <span class=\"fa fa-asterisk\" style=\"color: #ff7496;\"></span></label>"
	  + "<input type=\"text\" id=\"fname\" name=\"fname\" value=\"\" required/>"
	  + "</div>"
	  + "<div class=\"col-12\">"
	  + "<label for=\"email-address\">メールアドレス <span class=\"fa fa-asterisk\" style=\"color: #ff7496;\"></span></label>"
	  + "<input type=\"email\" id=\"email-address\" name=\"email-address\" value=\"\" required/>"
    + "</div>"
    + "<div class=\"col-2 col-4-mobile\">"
	  + "<label for=\"birthdate\">生年月日 <span class=\"fa fa-asterisk\" style=\"color: #ff7496;\"></span></label>"
	  + "<input type=\"date\" id=\"birthdate\" name=\"birthdate\" value=\"\" required/>"
    + "</div>"
    + "<div class=\"col-10 col-8-mobile\"></div>";

    $('#checkQuestionField').append(defaultQuestions);

    var reviewOptionScriptCode = "$('input[name=stars]').change(function() {"
    + "console.log('New star rating: ' + this.value);"
    + "});";

    $('<script>')
    .attr('type', 'text/javascript')
    .text(reviewOptionScriptCode)
    .appendTo('body');

    var params = {
      templateId : sessionStorage.getItem('templateId'),
      apiKey : sessionStorage.getItem('apiKey')
    }
    $.ajax({
        type    : 'GET',
        url     : '/apis/ajax/forms/getAllQuestions',
        data    : params,
        async   : true,
        success : function(data) {
            response = data["response"];
            
            if(response == '[]')
                return ;

            if(data['review_option']){
              var reviewOptionCode = '<div class="col-12">'
              + '<label for="stars">タップして評価して下さい <span class="fa fa-asterisk" style="color: #ff7496;"></span></label>'
              + '<label>'
              + '<div id="review">'
              + '<label>'
              + '<input type="radio" name="stars" value="1" />'
              + '<span class="fa fa-star"></span>'
              + '</label>'
              + '<label>'
              + '<input type="radio" name="stars" value="2" />'
              + '<span class="fa fa-star"></span>'
              + '<span class="fa fa-star"></span>'
              + '</label>'
              + '<label>'
              + '<input type="radio" name="stars" value="3" />'
              + '<span class="fa fa-star"></span>'
              + '<span class="fa fa-star"></span>'
              + '<span class="fa fa-star"></span>'
              + '</label>'
              + '<label>'
              + '<input type="radio" name="stars" value="4" />'
              + '<span class="fa fa-star"></span>'
              + '<span class="fa fa-star"></span>'
              + '<span class="fa fa-star"></span>'
              + '<span class="fa fa-star"></span>'
              + '</label>'
              + '<label>'
              + '<input type="radio" name="stars" value="5" />'
              + '<span class="fa fa-star"></span>'
              + '<span class="fa fa-star"></span>'
              + '<span class="fa fa-star"></span>'
              + '<span class="fa fa-star"></span>'
              + '<span class="fa fa-star"></span>'
              + '</label>'
              + '</div>'
              + '</div>'
              + '<div class="col-12">'
              + '<label for="review">レビューを書いてください <span class="fa fa-asterisk" style="color: #ff7496;"></span></label>'
              + '<textarea name="review" rows="3" required></textarea>'
              + '</div>';

              $('#checkQuestionField').append(reviewOptionCode);
            }

            var questionIds = response.substring(1, response.length-1).split(', ');
            for(var i = 0; i < questionIds.length; i++){
                var title = data['title'];
                $('#title').html('');
                $('#title').append(title);
                
                var description = data['description'];
                $('#description').html('');
                $('#description').append(description);

                var questionId = questionIds[i];
                var question = data[questionId];
                var questionType = data[questionId+'-type'];
                var requiredOption = data[questionId+'-required'];

                if(questionType == 'pulldown' || questionType == 'radio' || questionType == 'checkbox'){
                    var optionIds = data[questionId+'-optionIds'].substring(1, data[questionId+'-optionIds'].length-1).split(", ");
                    var options = data[questionId+'-options'].substring(1, data[questionId+'-options'].length-1).split(", ");
                    visualizeQuestion(questionId, question, questionType, requiredOption, optionIds, options, null, null);
                  } else if(questionType == 'date') {
                    var start = data[questionId+'-start'];
                    var end = data[questionId+'-end'];
                    visualizeQuestion(questionId, question, questionType, requiredOption, null, null, start, end);
                  } else if(questionType == 'time') {
                    var start = data[questionId+'-start'];
                    var end = data[questionId+'-end'];
                    visualizeQuestion(questionId, question, questionType, requiredOption, null, null, start, end);
                  } else {
                    visualizeQuestion(questionId, question, questionType, requiredOption, null, null, null, null);
                  }
            }

            var privacy = "<div class=\"col-12\">"
            + "<label>氏名、フリガナ、メールアドレス、生年月日以外の内容をイベントページに公開するグラフに追加してもよろしいですか？  <span class=\"fa fa-asterisk\" style=\"color: #ff7496;\"></span></label>"
            + "</div>"
            + "<div class=\"col-12\">"
            + "<input type=\"radio\" id=\"privacy-yes\" name=\"privacy\" value=\"yes\" checked/>"
            + "<label for=\"privacy-yes\">はい</label>"
            + "</div>"
            + "<div class=\"col-12\">"
            + "<input type=\"radio\" id=\"privacy-no\" name=\"privacy\" value=\"no\" />"
            + "<label for=\"privacy-no\">いいえ</label>"
            + "</div>";

            $('#checkQuestionField').append(privacy);

            var submitButton = "<div class=\"col-12\">"
            + "<ul class=\"actions\">"
            + "<li>"
            + "<input id=\"btn\" type=\"submit\" value=\"登録\"/>"
            + "</li>"
            + "</ul>"
            + "</div>";

            $('#checkQuestionField').append(submitButton);
        },
        error   : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("リクエスト時になんらかのエラーが発生しました：" + textStatus +":\n" + errorThrown);
        }
    });
}

function visualizeQuestion(questionId, question, questionType, requiredOption, optionIds, options, start, end){
  
  var questionHtml;

  if(questionType == 'address'){

    questionHtml = "<div class=\"col-12\"><label for=\""+questionId+"\"><h4>"+question+"</h4></label></div>"
    + "<div class=\"col-3 col-4-mobile\">"
    + "<input type=\"text\" id=\""+questionId+"-zip\" name=\""+questionId+"-zip\" maxlength=\"8\" placeholder=\"郵便番号\" onKeyUp=\"AjaxZip3.zip2addr(this,'','"+questionId+"','"+questionId+"')\" onBlur=\"AjaxZip3.zip2addr(this,'','"+questionId+"','"+questionId+"')\" "+getRequireOption(requiredOption)+"/>"
    + "</div>"
    + "<div class=\"col-9 col-8-mobile\">"
    + "<input type=\"text\" id=\""+questionId+"\" name=\""+questionId+"\" "+getRequireOption(requiredOption)+" />"
    + "</div>";
  
  } else {
    questionHtml = "<div class=\""+getWidth(questionType)+"\"><label for=\""+questionId+"\"><h4>"+question+" "+getRequiredHtml(requiredOption)+"</h4></label>"+getInputHtml(questionId, questionType, requiredOption, optionIds, options, start, end)+"</div>";
    if(questionType == 'date' || questionType == 'time'){
      questionHtml += "<div class=\"col-10 col-8-mobile\"></div>"
    }
  }

  $('#checkQuestionField').append(questionHtml);
}

function getInputHtml(questionId, questionType, requiredOption, optionIds, options, start, end){
  var inputHtml = '';

  if(questionType == 'pulldown') {

    inputHtml += "<select id=\""+questionId+"\" name=\""+questionId+"\" "+getRequireOption(requiredOption)+">"
    + "<option value=\"\" selected></option>";

    for(var i = 0; i < optionIds.length; i++){
      inputHtml += "<option value=\""+optionIds[i]+"\">"+options[i]+"</option>";
    }

    inputHtml += "</select>";

  } else if(questionType == 'radio') {
    
    for(var i = 0; i < optionIds.length; i++){
      inputHtml += "<div class=\"col-12\">"
      +"<input type=\""+questionType+"\" id=\""+optionIds[i]+"\" name=\""+questionId+"\" value=\""+options[i]+"\"><label for=\""+optionIds[i]+"\">"+options[i]+"</label>"
      +"</div>";
    }

  } else if(questionType == 'checkbox') {
    
    for(var i = 0; i < optionIds.length; i++){
      inputHtml += "<div class=\"col-12\">"
      +"<input type=\""+questionType+"\" id=\""+optionIds[i]+"\" name=\""+questionId+"\" value=\""+options[i]+"\"><label for=\""+optionIds[i]+"\">"+options[i]+"</label>"
      +"</div>";
    }

  } else if(questionType == 'paragraph') {
    
    inputHtml += "<textarea id=\""+questionId+"\" name=\""+questionId+"\" placeholder=\"\" rows=\"4\" "+getRequireOption(requiredOption)+"></textarea>"; 

  } else if(questionType == 'date') {

    inputHtml += "<input type=\"date\" id=\""+questionId+"\" name=\""+questionId+"\" min=\""+start+"\" max=\""+end+"\" value=\"\" "+getRequireOption(requiredOption)+"/>";
    
  } else if(questionType == 'time') {

    inputHtml += "<input type=\"time\" id=\""+questionId+"\" name=\""+questionId+"\" min=\""+start+"\" max=\""+end+"\" value=\"\" "+getRequireOption(requiredOption)+"/>";
    
  } else if(questionType == 'countryNames'){
    inputHtml += "<select id=\""+questionId+"\" name=\""+questionId+"\" "+getRequireOption(requiredOption)+">";
    inputHtml += countries_jp;
    inputHtml += "</select>";
  } else if(questionType == 'prefectures'){
    inputHtml += "<select id=\""+questionId+"\" name=\""+questionId+"\" "+getRequireOption(requiredOption)+">";
    inputHtml += prefectures;
    inputHtml += "</select>";
  } else {
    inputHtml += "<input id=\""+questionId+"\" type=\""+questionType+"\" name=\""+questionId+"\" value=\"\" "+getRequireOption(requiredOption)+"/>";
  }
  return inputHtml;
}

function getWidth(questionType) {
  if(questionType == 'date' || questionType == 'time') {
    return 'col-2 col-4-mobile';
  } else {
    return 'col-12';
  }
}

function getRequiredHtml(requiredOption){
  if(requiredOption == 'true') {
    return '<span class=\"fa fa-asterisk\" style=\"color: #ff7496;\"></span>';
  } else {
    return '';
  }
}

function getRequireOption(requiredOption) {
  if(requiredOption == 'true'){
    return 'required';
  } else {
    return '';
  }
}

