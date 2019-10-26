$(function() {
  $('.form-control').focus(formFocus);
});

function formFocus() {
  $('#btn')
    .removeClass()
    .html('送信 <span class="glyphicon glyphicon-send"></span>')
    .addClass('btn btn-success btn-lg btn-block');
}

function animate(){
	$('#btn')
    .removeClass()
    .html('処理中 <span class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></span>')
    .addClass('btn btn-primary btn-lg btn-block no-events');
}
