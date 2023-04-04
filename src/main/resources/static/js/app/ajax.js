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
    /**
     * 게시글 등록
     */
    save : function () {
        const data = {
            categoryBoard: $('#categoryBoard').val(),
            boardTitle: $('#boardTitle').val(),
            boardWriter: $('#boardWriter').val(),
            boardPassword: $('#boardPassword').val(),
            boardPasswordCheck: $('#boardPasswordCheck').val(),
            boardContent: $('#boardContent').val()
    };

        $.ajax({
            type: 'POST',
            url: '/free/write',
            dataType: 'text',
            contentType: 'application/json; charset=utf-8',
            // todo 서버에서 json으로 파싱한 데이터를 보내든지 그냥 text로 하든지 리펙토링 필수!
            data: JSON.stringify(data),
            success: function (res) {
                console.log(res)
                window.location.href = '/free'
            },
            error: function (data) {
                console.log(data)
            },
        })
    },
    /**
     * 게시글 수정
     */
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
            // todo 서버에서 json으로 파싱한 데이터를 보내든지 그냥 text로 하든지 리펙토링 필수!
            dataType: 'text',
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
    },
    /**
     * 게시글 삭제
     */
    delete: function () {
        const boardNo = $('#boardNo').val();

        $.ajax({
            type: 'DELETE',
            url: '/free/delete/' + boardNo,
            // todo 서버에서 json으로 파싱한 데이터를 보내든지 그냥 text로 하든지 리펙토링 필수!
            dataType: 'text',
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
