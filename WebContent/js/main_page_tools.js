$(document).ready(function() {
	$(".button-collapse").sideNav();
	$('.collapsible').collapsible();
	$("#delete_btn").click(function() {
		$("#delete_form").submit();
	});
});