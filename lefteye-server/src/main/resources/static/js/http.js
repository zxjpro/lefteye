var Http = function () {

}

Http.get = function (url , config) {
    var p = new Promise(function (resolve , reject) {
        axios.get(url , config).then(function(data){
            if(data.data.code == 0){
                resolve(data.data.data)
            }else{
                console.log(data)
                alert(data.data.msg)
            }
        }).catch(function(e){
            console.error(e)
            alert("请求失败")
        })

    })

    return p

}