app.service("userService",function ($http) {
    this.getName=function(){
        return $http.get("user/getName.do");
    }
})