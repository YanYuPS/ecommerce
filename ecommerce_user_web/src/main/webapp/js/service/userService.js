app.service("userService",function ($http) {
    this.add=function (entity) {
        return $http.post("../user/add.do",entity)
    }
})