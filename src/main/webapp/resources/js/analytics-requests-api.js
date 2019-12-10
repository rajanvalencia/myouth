function ajaxEditGraphPublicOption(questionId, option){
    var params = {
        templateId  : sessionStorage.getItem('templateId'),
        questionId  : questionId,
        option      : option,
        apiKey      : sessionStorage.getItem('apiKey')
    }
    
    $.ajax({
        type    : 'POST',
        url     : '/apis/ajax/analytics/editGraphPublicOption',
        data    : params,
        async   : true,
        success : function(data){
            response = data['res'];
        },
        error   :  function(XMLHttpRequest, textStatus, errorThrown) {
            alert("リクエスト時になんらかのエラーが発生しました：" + textStatus +":\n" + errorThrown);
        }
    });
}