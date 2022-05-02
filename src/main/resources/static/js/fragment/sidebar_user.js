function login() {
    $.ajax({
        url: "/api/v1/users/login",
        type: "post",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            email: $('#formInputEmail').val(),
            password: $('#formInputPassword').val()
        }),
        success: function (data) {
            if (data.length === 0) {
                Swal.fire('로그인 실패', '사용자가 존재하지 않습니다.', 'error');
            } else {
                let userName = data.nickname + '님 환영합니다.';
                Swal.fire('로그인 성공', userName, 'success').then(() => location.reload());
            }
        },
        error: function (e) {
            Swal.fire('로그인 실패', '사용자 정보가 일치하지 않습니다.', 'error');
        }
    });
}

function logout() {
    $.ajax({
        url: "/api/v1/users/logout",
        type: "get",
        success: function (data) {
            Swal.fire('로그아웃 되었습니다!', '다음에 또 만나요!', 'success').then(() => location.href = '/');
        },
        error: function (e) {
            Swal.fire('로그아웃 실패', '다시 로그아웃 해주세요.', 'error').then(() => location.href = '/');
        }
    });
}

$(document).ready(function () {
    $.ajax({
        url: "/api/v1/users/login",
        type: "get",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            let card_image = $('<img>', {class: 'card-img-top', src: data.imageUrl});
            card_image.appendTo("#user_container");

            let card_body = $('<div>', {class: 'card-body'});

            let card_name = $('<h5>', {class: 'card-title', text: data.nickname + '님 반갑습니다!'});
            card_name.appendTo(card_body);

            let card_text = $('<p>', {class: 'card-text', text: data.school.name});
            card_text.appendTo(card_body);

            let button1 = $('<a>', {
                href: "/users/" + data.id,
                class: 'btn btn-outline-dark',
                text: '마이페이지',
                style: "width:49%"
            });
            button1.appendTo(card_body);

            let button2 = $('<buttton>', {
                onclick: "logout()",
                class: 'btn btn-outline-dark',
                style: "width:49%;margin-left:2%",
                text: '로그아웃'
            });
            button2.appendTo(card_body);

            card_body.appendTo("#user_container");
        },
        error: function (e) {
            if (location.pathname !== '/') {
                Swal.fire('로그인 하슈!', '로그인을 해야 정보를 보여드림', 'warning').then(() => location.href = '/');
            }
            let card_body = $('<div>', {class: "card-body"});

            let email_form = $('<div>', {class: "form-group"});
            let email_label = $('<label>', {for: "formInputEmail", text: '이메일'});
            let email_input = $('<input>', {type: "email", class: "form-control", id: "formInputEmail"});
            email_label.appendTo(email_form);
            email_input.appendTo(email_form);
            email_form.appendTo(card_body);

            let password_form = $('<div>', {class: "form-group"});
            let password_label = $('<label>', {for: "formInputPassword", text: '비밀번호'});
            let password_input = $('<input>', {type: "password", class: "form-control", id: "formInputPassword"});
            password_label.appendTo(password_form);
            password_input.appendTo(password_form);
            password_form.appendTo(card_body);

            let button1 = $('<button>', {
                class: "btn btn-outline-dark",
                style: "width:49%",
                type: "button",
                onclick: "login()",
                text: '로그인'
            });
            button1.appendTo(card_body);
            let button2 = $('<button>', {
                class: "btn btn-outline-dark",
                style: "width:49%;margin-left:2%",
                type: "button",
                onclick: "location.href='/register'",
                text: '회원가입'
            });
            button2.appendTo(card_body);

            card_body.appendTo("#user_container");
        }
    });
});