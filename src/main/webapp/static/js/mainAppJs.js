/**
 * Created by Ajwar on 17.04.2017.
 */
var inputText,hostAndPortAdminServ/*,login*/;
init();

function init() {
/*    setTimeout(updateTableInfoServers,0);*/
    inputText={'goal':'get_list_servers'};
    setInterval(updateTableInfoServers,10000);
    $(document).ready(function () {
        hostAndPortAdminServ=$('#schemaManage').text()+'://'+$('#hostManage').text()+':'+$('#portManage').text();
        /*login=$('#loginManage').text()*/;
    });
}
/*function doAjax() {
    $.ajax({
        url : hostAndPortAdminServ,
        header:'Access-Control-Allow-Origin: *',
        type: 'POST',
        data : JSON.stringify(inputText),
        success : function (data) {
            var trHTML,opHTML,tempFirst;
            trHTML=opHTML=tempFirst='';
            $.each(data, function (i, item) {
                if (i==0) tempFirst=item.index;
                trHTML += "<tr><td>" + item.index + "</td><td>" + item.name + "</td><td>" + item.version + "</td><td>"+item.port + "</td><td>" +item.adminPort + "</td><td>"+item.count + "</td><td>" +item.memory + "</td><td>"+ item.station + "</td></tr>";
                opHTML += "<option value='" + item.index + "'>" + item.index + "</option>";
            });
            $('#indexSelect').find('option').remove().end().append(opHTML).val(tempFirst);
            $('#serverTable').find('tr:has(td)').remove().end().append(trHTML);
        }
    });
}*/
function updateTableInfoServers() {
    var NO_CONNECTION = "No connection";
    var trHTML, opHTML, tempFirst,colorTr;
    trHTML = opHTML = tempFirst =colorTr= '';
    $.postJSON(hostAndPortAdminServ, JSON.stringify(inputText),function (data) {
            $.each(data, function (i, item) {
                if (i == 0) tempFirst = item.index;
                if (item.station==0) colorTr=" bgcolor='red'";
                else colorTr="";
                trHTML += "<tr"+colorTr+"><td>" + item.index + "</td><td>" + item.name + "</td><td>" + item.version + "</td><td>" + item.port + "</td><td>" + item.adminPort + "</td><td>" + item.count + "</td><td>"+ item.memory + "</td><td>" + item.station + "</td></tr>";
                opHTML += "<option value='" + item.index + "'>" + item.index + "</option>";
            });
        $('#indexSelect').find('option').remove().end().append(opHTML).val(tempFirst);
        $('#serverTable').find('tr:has(td)').remove().end().append(trHTML);
        })
        .fail(function () {
        trHTML = "<tr bgcolor='red'><td>" + NO_CONNECTION + "</td><td>" + NO_CONNECTION + "</td><td>" + NO_CONNECTION + "</td><td>" + NO_CONNECTION + "</td><td>" + NO_CONNECTION + "</td><td>" + NO_CONNECTION + "</td><td>"+ NO_CONNECTION + "</td><td>" + NO_CONNECTION + "</td></tr>";
        $('#serverTable').find('tr:has(td)').remove().end().append(trHTML);
        console.log(trHTML);
    });
}

function createFile(filename, data) {
    var blob = new Blob([data], {type: 'text/csv'});
    if(window.navigator.msSaveOrOpenBlob) {
        window.navigator.msSaveBlob(blob, filename);
    }
    else{
        var elem = window.document.createElement('a');
        elem.href = window.URL.createObjectURL(blob);
        elem.download = filename;
        document.body.appendChild(elem);
        elem.click();
        document.body.removeChild(elem);
        window.URL.revokeObjectURL();
    }
}
function runCmd(login) {
    var cmdJsonObj=new Object();
    var cmdSelectBox=document.getElementById("cmdSelect");
    cmdJsonObj.login=login;
    cmdJsonObj.goal=cmdSelectBox.options[cmdSelectBox.selectedIndex].value;
    var indexSelectBox=document.getElementById("indexSelect");
    if (indexSelectBox.style.display == "") {
        cmdJsonObj.index=indexSelectBox.options[indexSelectBox.selectedIndex].value;
    }
    var strJson=JSON.stringify(cmdJsonObj);
    if (cmdJsonObj.goal=="send_options_server") {
        sendFile(cmdJsonObj.index,login);
    }else if (cmdJsonObj.goal=="get_options_server"){
        $.postJSON(hostAndPortAdminServ, strJson, function (data) {
            $.each(data, function (i, item) {
                createFile(item.nameFile, item.optionServer);
            })
        });
    }else {
        $.postJSON(hostAndPortAdminServ, strJson, function (data) {
            createTableAfterCmd(data);
        });
    }
}

function createTableAfterCmd(data) {
    $("#dataFromServer").find("tr").remove();
    $.each(data,function (i,item) {
        var strBody;
        if (i==0) {
            var strHead='';
            if (item.response!=undefined){
                strHead=createTableHead("Response server");
            /*}else if(item.version!=undefined){
                strHead=createTableHead("Server Name","Port","Admin Port","Version","Online");*/
            }else {
                strHead=createTableHead("OS and Arch","% free memory","% free swap memory","Load average","System cpu load",
                    "Process cpu load","Count all process","Count all threads","Process cpu Time");
            }
            $('#dataFromServer').find("thead").append(strHead);
        }

        if (item.response!=undefined){
            strBody=createTableBody(item.response);
        /*}else if(item.version!=undefined){
            strBody=createTableBody(item.serverName,item.port,item.adminPort,item.version,item.station);*/
        }else {
            strBody=createTableBody(item.osAndArch,item.freeMemory,item.swapMemory,item.loadAverage,item.systemCpuLoad,
                item.processCpuLoad,item.countProcess,item.countThreads,item.processCpuTime);
        }
        $("#dataFromServer").find("tbody").append(strBody);
    });
}

function sendFile(index,login) {
    var file=$("#sendFileSetup")[0].files[0];
    var obj=new Object();
    obj.goal="send_options_server";
    obj.index=index;
    obj.login=login;
    var reader=new FileReader();
    reader.onload=function () {
        obj.optionServer=this.result;
        $.postJSON(hostAndPortAdminServ, JSON.stringify(obj), function (data) {
            createTableAfterCmd(data);
        })
    };
    reader.readAsText(file);
}

function createTableBody() {
    var body="<tr>";
    for (var i=0;i<arguments.length;i++) {
        body += "<td>" + arguments[i] + "</td>";
    }
    body+="</tr>";
    return body;
}
function createTableBodyVertical() {
    var tr="<tr>",trClose="</tr>";
    var body="";
    for (var i=0;i<arguments.length;i++) {
        body += tr+"<td>" + arguments[i] + "</td>"+trClose;
    }
    body+="";
    return body;
}

function createTableHead(){
    var head="<tr>";
    for(var i=0;i<arguments.length;i++){
        head+="<th>"+arguments[i]+"</th>";
    }
    head+="</tr>";
    return head;
}
function createTableHeadVertical(){
    var tr="<tr>",trClose="</tr>";
    var head="";
    for(var i=0;i<arguments.length;i++){
        head+=tr+"<th colspan='"+arguments.length+"'>"+arguments[i]+"</th>"+trClose;
    }
    return head;
}

function selectCmd(selectBox) {
    var selectedValue = selectBox.options[selectBox.selectedIndex].value;
    switch (selectedValue){
/*        case "get_list_servers":*/
        case "get_total_info_system":
            hideElement("none","indexSelect","textServers","sendFileForm");
            break;
/*        case "serverinfo":*/
        case "get_options_server":
        case "stop":
        case "run":
        /*case "channels_count":*/
            hideElement("","indexSelect","textServers");
            hideElement("none","sendFileForm");
            break;
        case "send_options_server":
            hideElement("","indexSelect","textServers","sendFileForm");
            break;
    }

}
function hideElement() {
    for(var i=1;i<arguments.length;i++){
        document.getElementById(arguments[i]).style.display=arguments[0];
    }
}
jQuery.extend({
    postJSON: function( url, data, callback) {
        return jQuery.post(url, data, callback, "json");
    }
});