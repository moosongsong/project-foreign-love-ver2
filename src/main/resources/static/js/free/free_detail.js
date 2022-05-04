function getDetail() {
    $.ajax({
        url: "/api/v1/free/" + location.pathname.split("/")[2],
        type: "get",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        async: false,
        success: function (data) {
            if (data.length === 0) {
                location.replace("/not-found");
            } else {
                $('#title').text(data.title);
                let createdAtList = data.createdAt.split('T');
                $('#createdAt_writer').text(createdAtList[0] + ' ' + createdAtList[1] + '  By ' + data.user.nickname);
                $('#school').text(data.school.name);
                $('#nation').text(data.nation.name);
                $("#content").text(data.content);
                if (data.imageUrl != null) {
                    let dataImage = $('<img>', {class: 'img-fluid rounded', src: data.imageUrl});
                    dataImage.appendTo("#image_box");
                }
            }
        },
        error: function (e) {
            location.replace("/not-found");
        }
    });

    $.ajax({
        url: "/api/v1/free/check/" + location.pathname.split("/")[2],
        type: "get",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        async: false,
        success: function (data) {
            if (data === true) {
                // let section_body = $('<section>', {class: "mb-4",style:"text-align: right"});
                let section_body = $('#owner_container');
                section_body.addClass("mb-4");
                section_body.css("text-align", "right");
                let button1 = $('<button>', {
                    class: "btn btn-outline-dark disabled",
                    type: "button",
                    text: '수정하기'
                });
                let button2 = $('<button>', {
                    class: "btn btn-outline-dark ms-2",
                    type: "button",
                    text: '삭제하기'
                });
                button1.appendTo(section_body);
                button2.appendTo(section_body);
            }
        },
        error: function (e) {
            location.replace("/not-found");
        }
    });
}

$(document).ready(function () {
    $('#free_li').addClass('active');
    getDetail();
});