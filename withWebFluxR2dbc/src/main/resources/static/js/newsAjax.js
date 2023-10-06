
//************** SPECIF CRUD ********

window.onload=function(){
	(document.getElementById("inputId")).disabled=true; //if auto_incr
	initListeners(); 
}

function objectFromInput(){
	let id = (document.getElementById("inputId")).value;
	if(id=="")id=null;
	let title = (document.getElementById("inputTitle")).value;
	let text = (document.getElementById("inputText")).value;
	let date = (document.getElementById("inputDate")).value;
	if(date==null || date==""){
		date = (new Date()).toISOString().split("T")[0];
	}
	document.getElementById("spanMsg").innerHTML="";
	let obj = { id : id,
			    title : title,
				text : text,
	            date : date };
	return obj;
}

function displayObject(obj){
	(document.getElementById("inputId")).value=obj.id;
	(document.getElementById("inputTitle")).value=obj.title;
	(document.getElementById("inputText")).value=obj.text;
	(document.getElementById("inputDate")).value=obj.date;
}

function insertRowCells(row,obj){
	(row.insertCell(0)).innerHTML = obj.id;
	(row.insertCell(1)).innerHTML = obj.title;
	(row.insertCell(2)).innerHTML = obj.text;
	(row.insertCell(3)).innerHTML = obj.date;
}


function blankObject(){
	return {id:"" , title: "" ,  text: "" , date : (new Date()).toISOString().split("T")[0]};	
}

function getWsBaseUrl(){
	return "./rest/api-news/news";	
}

//obj = object with values to check
//action = "add" or "update" or ...
function canDoAction(action,obj){
	ok=true; //by default
	if(obj.text==null || obj.text == "")
	  ok=false;
	
	return ok;
}