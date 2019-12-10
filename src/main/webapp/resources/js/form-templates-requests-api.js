function ajaxEditPublicOption(templateId, option){
    var params = {
        templateId : templateId,
        event   : sessionStorage.getItem('event'),
        option : option,
        apiKey : sessionStorage.getItem('apiKey')
    }

    $.ajax({
        type    : 'POST',
        url     : '/apis/ajax/forms/editTemplatePublicOption',
        data    : params,
        async   : true,
        success : function(data) {
            response = data["response"];
        },
        error   : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("リクエスト時になんらかのエラーが発生しました：" + textStatus +":\n" + errorThrown);
        }
    });
}

function ajaxEditReviewOption(templateId, option){
    var params = {
        templateId : templateId,
        event   : sessionStorage.getItem('event'),
        option : option,
        apiKey : sessionStorage.getItem('apiKey')
    }

    $.ajax({
        type    : 'POST',
        url     : '/apis/ajax/formTemplates/editReviewOption',
        data    : params,
        async   : true,
        success : function(data){
            var res = data['response'];
        },
        errorThrown : function(XMLHttpRequest, textStatus, errorThrown){
            alert("リクエスト時になんらかのエラーが発生しました：" + textStatus +":\n" + errorThrown);
        }
    })
}

function ajaxAddTemplate(event, templateId, title){
    var params = {
        templateId : templateId,
        event : event,
        title : title,
        apiKey : sessionStorage.getItem('apiKey')
    }

    $.ajax({
        type    : 'POST',
        url     : '/apis/ajax/forms/addTemplate',
        data    : params,
        async   : true,
        success : function(data) {
            response = data["response"];
        },
        error   : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("リクエスト時になんらかのエラーが発生しました：" + textStatus +":\n" + errorThrown);
        }
    });
}

function ajaxDeleteTemplate(templateId){
    var params = {
        templateId : templateId,
        event   : sessionStorage.getItem('event'),
        apiKey : sessionStorage.getItem('apiKey')
    }

    $.ajax({
        type    : 'POST',
        url     : '/apis/ajax/forms/deleteTemplate',
        data    : params,
        async   : true,
        success : function(data) {
            response = data["response"];
        },
        error   : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("リクエスト時になんらかのエラーが発生しました：" + textStatus +":\n" + errorThrown);
        }
    });
}