/**
 * 
 */

var selectAll = "Select all regions";
var unSelectAll = "Unselect all regions";

function selectFunctionality(currentElem) {
	var checkboxes = currentElem.closest('form').find('.checkBoxClass');
	
	if (currentElem.text() == selectAll) {
		checkboxes.prop('checked', true);
		currentElem.html(unSelectAll)
	} else {
		checkboxes.prop('checked', false);
		currentElem.html(selectAll)
	}
}
	
$("#includedContentCreateAlarm").find("#selectAllReg").click( function() {
	selectFunctionality($(this))
});

$("#includedContentCreateUser").find("#selectAllReg").click( function () {
	selectFunctionality($(this))
});
