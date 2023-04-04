var main = {
    init : function () {
        const _this = this;

        // 게시글 등록
        $('#btn-save').on('click', function () {
            _this.save();
        });

        // 게시글 수정
        $('#btn-modify').on('click', function () {
            _this.modify();
        })

        // 게시글 삭제
        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },
    save : function () {
        const data = {
            categoryBoard: $('#categoryBoard').val(),
            boardTitle: $('#boardTitle').val(),
            boardWriter: $('#boardWriter').val(),
            boardPassword: $('#boardPassword').val(),
            boardPasswordCheck: $('#boardPasswordCheck').val(),
            boardContent: $('#boardContent').val()
    };

        // todo window.reload 안됨
        $.ajax({
            type: 'POST',
            url: '/free/write',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function (res) {
                console.log(res)
                window.location.href = '/free'
            },
            error: function (data) {
                console.log(data)
            },
        })
    },//save
    modify : function () {
        const data = {
            categoryBoard: $('#categoryBoard').val(),
            boardTitle: $('#boardTitle').val(),
            boardWriter: $('#boardWriter').val(),
            boardContent: $('#boardContent').val()
        };

        const boardNo = $('#boardNo').val();

        $.ajax({
            type: 'PUT',
            url: '/free/modify/' + boardNo,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function (res) {
                console.log(res)
                window.location.href = '/free'
            },
            error: function (data) {
                console.log(data)
                alert('게시글 수정 실패')
            },
        })
    },//modify
    delete: function () {
        const boardNo = $('#boardNo').val();

        $.ajax({
            type: 'DELETE',
            url: '/free/delete/' + boardNo,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            success: function () {
                alert('게시글 삭제');
                window.location.href = '/free'
            },
            //todo ??error 가 정상적으로 실행된다;;
            error: function () {
                alert('게시글 삭제')
                window.location.href = '/free'
            }
        })
    },
}

main.init();
