$.ajax({
        type    : 'GET',
        url     : '/apis/ajax/forms/reviewFeature',
        data    : params,
        async   : true,
        success : function(data) {
            response = data["response"];
            
            if(response == '[]')
                return ;

            title = data['title'];
            $('#title').html('');
            $('#title').append(title);

            description = data['description'];
            $('#description').html('');
            $('#description').append(description);

            var questionIds = response.substring(1, response.length-1).split(', ');
            for(var i = 0; i < questionIds.length; i++){
                var questionId = questionIds[i];
                var question = data[questionId];
                var questionType = data[questionId+'-type'];
                var questionRequired = data[questionId+'-required'];

                generateQuestion(questionId, question, questionType, questionRequired);

                if(questionType == 'pulldown' || questionType == 'radio' || questionType == 'checkbox'){
                    var optionIds = data[questionId+'-optionIds'].substring(1, data[questionId+'-optionIds'].length-1).split(", ");
                    var options = data[questionId+'-options'].substring(1, data[questionId+'-options'].length-1).split(", ");
                    addOptionHtml(questionId, optionIds, options);
                  } else if(questionType == 'date') {
                    var start = data[questionId+'-start'];
                    var end = data[questionId+'-end'];
                    addDatePeriodHtml(questionId, start, end);
                  } else if(questionType == 'time') {
                    var start = data[questionId+'-start'];
                    var end = data[questionId+'-end'];
                    addTimePeriodHtml(questionId, start, end);
                  }
            }
        },
        error   : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("リクエスト時になんらかのエラーが発生しました：" + textStatus +":\n" + errorThrown);
        }
    });