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

    $.ajax({
        url: "/api/v1/comments/" + location.pathname.split("/")[2],
        type: "get",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        async: false,
        success: function (data) {
            let comment_area = $('#comments_area');
            for (const datum of data) {
                let comment_body = $('<div>', {class: "d-flex mb-4"});
                let user_image_body = $('<div>', {class: "flex-shrink-0"});
                let user_image;
                if (!datum.user.imageUrl) {
                    user_image = $('<img>', {
                        class: 'rounded-circle',
                        src: 'https://moosongsong.s3.ap-northeast-2.amazonaws.com/foreignlove/images.png',
                        style: 'width:50px'
                    });
                } else {
                    user_image = $('<img>', {class: 'rounded-circlep', src: datum.user.imageUrl, style: 'width:50px'});
                }
                user_image.appendTo(user_image_body);
                user_image_body.appendTo(comment_body);

                let comment_content_body = $('<div>', {class: "ms-3"});
                let comment_content = $('<span>', {class: "fw-bold me-2", text: datum.user.nickname});
                comment_content.appendTo(comment_content_body);

                let school = $('<span>', {
                    class: 'badge bg-secondary text-decoration-none link-light',
                    text: datum.user.school
                });
                school.appendTo(comment_content_body);

                let content = $('<div>', {class: "my-1", text: datum.content});
                content.appendTo(comment_content_body);
                comment_content_body.appendTo(comment_body);

                comment_body.appendTo(comment_area);
            }

            console.log(data);
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function deletePost() {
    $.ajax({
        url: "/api/v1/free/" + location.pathname.split("/")[2],
        type: "delete",
        success: function (data) {
            Swal.fire('삭제 성공', "게시글이 삭제되었어욧!", 'success').then(() => location.href = '/free');
        },
        error: function (e) {
            Swal.fire('삭제 실패', "한번 더 시도해주세요!", 'error');
        }
    });
}

function postComment() {
    $.ajax({
        url: "/api/v1/comments",
        type: "post",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            boardId: location.pathname.split("/")[2],
            content: $('#comment').val(),
        }),
        success: function (data) {
            Swal.fire('등록 성공', "게시글에 댓글이 등록되었어욧!", 'success').then(() => location.reload());
        },
        error: function (e) {
            Swal.fire('등록 실패', "한번 더 시도해주세요!", 'error');
        }
    });
}

$(document).ready(function () {
    $('#free_li').addClass('active');
    getDetail();
});