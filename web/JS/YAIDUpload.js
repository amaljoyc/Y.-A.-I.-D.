/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function YAIDUpload(toLoc){
    
    var htmlcont="<form id=\"file_upload\" action=\"Upload\" method=\"POST\" enctype=\"multipart/form-data\">"+
    "<div id=\"drop_zone_1\">"+
    "<input id=\"file_1\" type=\"file\" name=\"file_1\" multiple/>"+
    "<div>Upload files to:<label id='upload-loc'>"+toLoc+"</label><hiddern id='upload-loc-hid'value=\""+encodeURI(toLoc)+"\" name=\"toloc\"></div></div>"+
    //    "<div id=\"drop_zone_2\">"+
    //    "<input id=\"file_2\" type=\"file\" name=\"file_2\" multiple/>"+
    //    "<div>Upload files</div></div>"+
    //    "<div id=\"drop_zone_3\">"+
    //    "<input id=\"file_3\" type=\"file\" name=\"file_3\" multiple/>"+
    //    "<div>Upload files</div></div>"+
    "<button>Upload</button>"+

    "<table id=\"files_1\" class=\"uploaded-file-list\"></table>"+
    //    "<table id=\"files_2\" style=\"background:gold;\"></table>"+
    //    "<table id=\"files_3\" style=\"background:orange;\"></table>"+
    "</form>";
    if(YAIDUpload.upload_wind===null){
        YAIDUpload.upload_wind=Window({
            'option':{
                'title':'Upload',
                height:300,
                width:230
            },
            'content':"",
            "ajax":false
        });
        $('#windowe_content'+ YAIDUpload.upload_wind.attr("id")).append(htmlcont);
        this.initFileUpload(1);
    }else{
        // $(YAIDUpload.upload_wind).open();
        
        $("#upload-loc").html(toLoc);
        $("#upload-loc-hid").attr("value",toLoc);
    }
    this.setUploadLocation(toLoc);
    console.log(YAIDUpload.upload_wind);
}
YAIDUpload.upload_wind=null;

YAIDUpload.prototype.initFileUpload = function (suffix) {
    var temp=this;
    $('#file_upload').fileUploadUI({
        namespace: 'file_upload_' + suffix,
        fileInputFilter: '#file_' + suffix,
        dropZone: $('#drop_zone_' + suffix),
        uploadTable: $('#files_' + suffix),
        downloadTable: $('#files_' + suffix),
        buildUploadRow: function (files, index) {
            return $('<tr><td>' + files[index].name + '<\/td>' +
                '<td class="file_upload_progress"><div><\/div><\/td>' +
                '<td class="file_upload_cancel">' +
                '<button class="ui-state-default ui-corner-all" title="Cancel">' +
                '<span class="ui-icon ui-icon-cancel">Cancel<\/span>' +
                '<\/button><\/td><\/tr>');
        },
        buildDownloadRow: function (file) {
            temp.updateFolder(file);
            return $('<tr><td>' + file.name + '<\/td><\/tr>');
        }
    });
};

YAIDUpload.prototype.setUploadLocation = function (toLoc) {
    dataToSend={"toloc":encodeURI(toLoc)};
    $.ajax({
            type: 'GET',
            url: "Upload",								 
            data: dataToSend,
            success: function (){
                console.log("Upload Path set!!!");                
            },
            error: function(jqXHR, textStatus, errorThrown){
                console.log(jqXHR, textStatus, errorThrown)
            }
        });
};

YAIDUpload.prototype.updateFolder = function (file) {
    console.log(file);
};