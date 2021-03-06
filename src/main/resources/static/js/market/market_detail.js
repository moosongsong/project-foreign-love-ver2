function getDetail() {
    $.ajax({
        url: "/api/v1/market/" + location.pathname.split("/")[2],
        type: "get",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        async: false,
        success: function (data) {
            if (data.length === 0) {
                location.replace("/not-found");
            } else {
                $('title').text(data.title);
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
                if (data.step === 'DONE') {
                    $("#cost").text('거래 완료 되었요!');
                } else {
                    if (data.type === 'BUY') {
                        $("#cost").text(data.cost + '원에 사고 싶어요!');
                    } else {
                        $("#cost").text(data.cost + '원에 팔고 싶어요!');
                    }
                }
            }
        },
        error: function (e) {
            location.replace("/not-found");
        }
    });

    $.ajax({
        url: "/api/v1/market/check/" + location.pathname.split("/")[2],
        type: "get",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        async: false,
        success: function (data) {
            if (data === true) {
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
                    text: '삭제하기',
                    onclick: 'deletePost()'
                });
                button1.appendTo(section_body);
                button2.appendTo(section_body);
            }
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function deletePost() {
    $.ajax({
        url: "/api/v1/market/" + location.pathname.split("/")[2],
        type: "delete",
        success: function (data) {
            Swal.fire('삭제 성공', "게시글이 삭제되었어욧!", 'success').then(() => location.href = '/market');
        },
        error: function (e) {
            Swal.fire('삭제 실패', "한번 더 시도해주세요!", 'error');
        }
    });
}

$(document).ready(function () {
    $('#market_li').addClass('active');
    getDetail();
});