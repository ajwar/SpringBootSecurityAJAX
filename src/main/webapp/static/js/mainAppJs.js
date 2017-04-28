/**
 * Created by Ajwar on 17.04.2017.
 */
var inputText,hostAndPortAdminServ;
init();

function init() {
/*    setTimeout(updateTableInfoServers,0);*/
    setInterval(updateTableInfoServers,10000);
    //setInterval(doAjax,10000);
    hostAndPortAdminServ='http://127.0.0.1:5505';
    inputText={'goal':'get_list_servers'};
}
function doAjax() {
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
                trHTML += "<tr><td>" + item.index + "</td><td>" + item.name + "</td><td>" + item.version + "</td><td>"+item.port + "</td><td>" +item.adminPort + "</td><td>"+item.count + "</td><td>" + item.station + "</td></tr>";
                opHTML += "<option value='" + item.index + "'>" + item.index + "</option>";
            });
            //$("tr:has(td)").remove();
            //$('#serverTable').append(trHTML);
            $('#indexSelect').find('option').remove().end().append(opHTML).val(tempFirst);
            $('#serverTable').find('tr:has(td)').remove().end().append(trHTML);
        }
    });
}

function updateTableInfoServers() {
    //$.getJSON(hostAndPortAdminServ,inputText,function (data) {
    $.getJSON(hostAndPortAdminServ,JSON.stringify(inputText),function (data) {
        var trHTML,opHTML,tempFirst;
        trHTML=opHTML=tempFirst='';
        $.each(data, function (i, item) {
            if (i==0) tempFirst=item.index;
            trHTML += "<tr><td>" + item.index + "</td><td>" + item.name + "</td><td>" + item.version + "</td><td>"+item.port + "</td><td>" +item.adminPort + "</td><td>"+item.count + "</td><td>" + item.station + "</td></tr>";
            opHTML += "<option value='" + item.index + "'>" + item.index + "</option>";
        });
        //$("tr:has(td)").remove();
        //$('#serverTable').append(trHTML);
        $('#indexSelect').find('option').remove().end().append(opHTML).val(tempFirst);
        $('#serverTable').find('tr:has(td)').remove().end().append(trHTML);
    });
}

/*function createFile(fileName,text) {
    var element = document.createElement('a');
    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
    element.setAttribute('download', fileName);
    element.style.display = 'none';
    document.body.appendChild(element);
    element.click();
    document.body.removeChild(element);
}*/

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
function runCmd() {
    var cmdJsonObj=new Object();
    var cmdSelectBox=document.getElementById("cmdSelect");
    cmdJsonObj.goal=cmdSelectBox.options[cmdSelectBox.selectedIndex].value;
    var indexSelectBox=document.getElementById("indexSelect");
    if (indexSelectBox.style.display == "") {
        cmdJsonObj.index=indexSelectBox.options[indexSelectBox.selectedIndex].value;
    }
    var strJson=JSON.stringify(cmdJsonObj);
    if (cmdJsonObj.goal=="send_options_server") {
        sendFile(cmdJsonObj.index);
    }else if (cmdJsonObj.goal=="get_options_server"){
        $.getJSON(hostAndPortAdminServ,strJson,function (data) {
            $.each(data,function (i, item) {
                createFile(item.nameFile,item.optionServer);
            })
        });
    }else {
        $.getJSON(hostAndPortAdminServ,strJson,function (data) {
            createTableAfterCmd(data);
        });
    }
}

function createTableAfterCmd(data) {
    $("#dataFromServer").find("tr").remove();
    $.each(data,function (i,item) {
        var strBody;
        if (i==0) {
            var strHead;
            //window.alert(item.response+"=response");
            if (item.response!=undefined){
                strHead=createTableHeadVertical("Response server");
                /*strHead=createTableHead("Response server");*/
            /*}else if(item.version!=undefined){
                strHead=createTableHead("Server Name","Port","Admin Port","Version","Online");*/
            }else {
                strHead=createTableHeadVertical("OS and Arch","% free memory","% free swap memory","Load average","System cpu load",
                    "Process cpu load","Count all process","Count all threads","Process cpu Time");
                /*strHead=createTableHead("OS and Arch","% free memory","% free swap memory","Load average","System cpu load",
                    "Process cpu load","Count all process","Count all threads","Process cpu Time");*/
            }
            $('#dataFromServer').find("thead").append(strHead);
        }

        if (item.response!=undefined){
            //strBody=createTableBody(item.response);
            strBody=createTableBodyVertical(item.response);
        /*}else if(item.version!=undefined){
            strBody=createTableBody(item.serverName,item.port,item.adminPort,item.version,item.station);*/
        }else {
            strBody=createTableBodyVertical(item.osAndArch,item.freeMemory,item.swapMemory,item.loadAverage,item.systemCpuLoad,
                item.processCpuLoad,item.countProcess,item.countThreads,item.processCpuTime);
            /*strBody=createTableBody(item.osAndArch,item.freeMemory,item.swapMemory,item.loadAverage,item.systemCpuLoad,
                item.processCpuLoad,item.countProcess,item.countThreads,item.processCpuTime);*/
        }
        $("#dataFromServer").find("tbody").append(strBody);
    });
}

function sendFile(index) {
    var file=$("#sendFileSetup")[0].files[0];
    var obj=new Object();
    obj.goal="send_options_server";
    obj.index=index;
    var reader=new FileReader();
    reader.onload=function () {
        obj.optionServer=this.result;
        console.log(this.result);
        $.getJSON(hostAndPortAdminServ,JSON.stringify(obj),function (data) {
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
    console.log("body===="+body);
    return body;
}
function createTableBodyVertical() {
    var tr="<tr>",trClose="</tr>";
    var body="";
    for (var i=0;i<arguments.length;i++) {
        body += tr+"<td>" + arguments[i] + "</td>"+trClose;
    }
    body+="";
    console.log("body===="+body);
    return body;
}

function createTableHead(){
    var head="<tr>";
    for(var i=0;i<arguments.length;i++){
        head+="<th>"+arguments[i]+"</th>";
    }
    head+="</tr>";
    console.log("head===="+head);
    return head;
}
function createTableHeadVertical(){
    var tr="<tr>",trClose="</tr>";
    var head="";
    for(var i=0;i<arguments.length;i++){
        head+=tr+"<th colspan='"+arguments.length+"'>"+arguments[i]+"</th>"+trClose;
    }
    console.log("head===="+head);
    return head;
}

function selectCmd(selectBox) {
    var selectedValue = selectBox.options[selectBox.selectedIndex].value;
    switch (selectedValue){
/*        case "get_list_servers":*/
        case "get_total_info_system":
            document.getElementById("indexSelect").style.display="none";//hide select
            document.getElementById("textServers").style.display="none";//hide select
            document.getElementById("sendFileForm").style.display="none";//hide select
            break;
/*        case "serverinfo":*/
        case "get_options_server":
        case "stop":
        case "run":
        /*case "channels_count":*/
            document.getElementById("indexSelect").style.display="";//show select
            document.getElementById("textServers").style.display="";//show select
            document.getElementById("sendFileForm").style.display="none";//show select
            break;
        case "send_options_server":
            document.getElementById("indexSelect").style.display="";//show select
            document.getElementById("textServers").style.display="";//show select
            document.getElementById("sendFileForm").style.display="";//show select
            break;
    }
}
