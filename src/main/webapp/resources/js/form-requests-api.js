function sendRequestViaAjax(type, url, params, async){

    $.ajax({
        type    : type,             //GET or POST
        url     : url,              //送信先のServlet URL
        data    : params,           //リクエストJSON
        async   : Boolean(async),   //true:非同期(デフォルト), false:同期
        success : function(data) {  //通信が成功した場合に受け取るメッセージ
          response = data["res"];
          return response;
        },
        error   : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("リクエスト時になんらかのエラーが発生しました：" + textStatus +":\n" + errorThrown);
        }
    });

}

function ajaxEditTitle(title){

    var params = {
        templateId : sessionStorage.getItem('templateId'),
        event : sessionStorage.getItem('event'),
        title : title,
        apiKey : sessionStorage.getItem('apiKey')
    }

    var type = 'POST';
    var url = '/apis/ajax/forms/editTitle';
    var async = 'true';

    sendRequestViaAjax(type, url, params, async);
}

function ajaxEditDescription(description){
    var params = {
        templateId : sessionStorage.getItem('templateId'),
        event : sessionStorage.getItem('event'),
        description : description,
        apiKey : sessionStorage.getItem('apiKey')
    }

    var type = 'POST';
    var url = '/apis/ajax/forms/editDescription';
    var async = 'true';

    sendRequestViaAjax(type, url, params, async);
}

function ajaxAddQuestion(questionId, question, questionType, required){
    var params = {
        templateId : sessionStorage.getItem('templateId'),
        event : sessionStorage.getItem('event'),
        questionId : questionId,
        question : question,
        questionType : questionType,
        required : required,
        apiKey : sessionStorage.getItem('apiKey')
    }

    var type = 'POST';
    var url = '/apis/ajax/forms/addQuestion';
    var async = 'true';

    sendRequestViaAjax(type, url, params, async);
}

function ajaxAddDateOrTimePeriod(questionId, questionType, start, end){
    var params = {
        templateId : sessionStorage.getItem('templateId'),
        event : sessionStorage.getItem('event'),
        questionId :  questionId,
        questionType : questionType, 
        start : start,
        end : end,
        apiKey : sessionStorage.getItem('apiKey')
    }

    var type = 'POST';
    var url = '/apis/ajax/forms/addDateOrTimePeriod';
    var async = 'true';

    sendRequestViaAjax(type, url, params, async);
}

function ajaxEditQuestion(questionId, question){
    var params = {
        templateId : sessionStorage.getItem('templateId'),
        event : sessionStorage.getItem('event'),
        questionId : questionId,
        question : question,
        apiKey : sessionStorage.getItem('apiKey')
    }

    var type = 'POST';
    var url = '/apis/ajax/forms/editQuestion';
    var async = 'true';

    sendRequestViaAjax(type, url, params, async);
}


function ajaxEditQuestionType(questionId, questionType){
    var params = {
        templateId : sessionStorage.getItem('templateId'),
        event : sessionStorage.getItem('event'),
        questionId : questionId,
        questionType : questionType,
        apiKey : sessionStorage.getItem('apiKey')
    }

    var type = 'POST';
    var url = '/apis/ajax/forms/editQuestionType';
    var async = 'true';
    
    sendRequestViaAjax(type, url, params, async);
}

function ajaxEditRequiredOption(questionId, required){
    var params = {
        templateId : sessionStorage.getItem('templateId'),
        event : sessionStorage.getItem('event'),
        questionId : questionId,
        required : required,
        apiKey : sessionStorage.getItem('apiKey')
    }

    var type = 'POST';
    var url = '/apis/ajax/forms/editRequiredOption';
    var async = 'true';

    sendRequestViaAjax(type, url, params, async);
}

function ajaxAddOption(questionId, optionId, option){
    var params = {
        templateId : sessionStorage.getItem('templateId'),
        event : sessionStorage.getItem('event'),
        questionId : questionId,
        optionId : optionId,
        option : option,
        apiKey : sessionStorage.getItem('apiKey')
    }

    var type = 'POST';
    var url = '/apis/ajax/forms/addOption';
    var async = 'true';

    sendRequestViaAjax(type, url, params, async);
}

function ajaxEditOption(questionId, optionId, option){
    var params = {
        templateId : sessionStorage.getItem('templateId'),
        event : sessionStorage.getItem('event'),
        questionId : questionId,
        optionId : optionId,
        option : option,
        apiKey : sessionStorage.getItem('apiKey')
    }

    var type = 'POST';
    var url = '/apis/ajax/forms/editOption';
    var async = 'true';

    sendRequestViaAjax(type, url, params, async);
}

function ajaxEditDateOrTimePeriod(questionId, questionType, start, end){
    var params = {
        templateId : sessionStorage.getItem('templateId'),
        event : sessionStorage.getItem('event'),
        questionId :  questionId,
        questionType : questionType, 
        start : start,
        end : end,
        apiKey : sessionStorage.getItem('apiKey')
    }

    var type = 'POST';
    var url = '/apis/ajax/forms/editDateOrTimePeriod';
    var async = 'true';

    sendRequestViaAjax(type, url, params, async);
}

function ajaxDeleteOption(questionId, optionId){
    var params = {
        templateId : sessionStorage.getItem('templateId'),
        event : sessionStorage.getItem('event'),
        questionId : questionId,
        optionId : optionId,
        apiKey : sessionStorage.getItem('apiKey')
    }

    var type = 'POST';
    var url = '/apis/ajax/forms/deleteOption';
    var async = 'true';

    sendRequestViaAjax(type, url, params, async);

    $("#"+optionId).remove();
}

function ajaxDeleteQuestion(questionId){
    var params = {
        templateId : sessionStorage.getItem('templateId'),
        event : sessionStorage.getItem('event'),
        questionId : questionId,
        apiKey : sessionStorage.getItem('apiKey')
    }

    var type = 'POST';
    var url = '/apis/ajax/forms/deleteQuestion';
    var async = 'true';

    sendRequestViaAjax(type, url, params, async);

    $("#"+questionId).remove();
}