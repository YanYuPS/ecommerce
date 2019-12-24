var app=angular.module('ecommerce', ['pagination']);//定义模块

app.controller('brandController' ,function($scope,$http){

    /**
     * 全部 品牌列表
    //  */
    $scope.findAll=function(){
        $http.get('../brand/findAll.do').success(
            function(response){
                $scope.brandList=response;
            }
        );
    }


    /**
     * 分页控件
     * @type {{totalItems: number, onChange: onChange, itemsPerPage: number, currentPage: number, perPageOptions: number[]}}
     */
    $scope.paginationConf={
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function(){
            $scope.reloadList();
        }
    }
    /**
     * 分页控件--重新加载
     */
    $scope.reloadList=function(){
        console.log("重新加载")
        // console.log($scope.searchBrand!=null)
        // if($scope.searchBrand){
        //     $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage)
        // }else{
        //     $scope.findPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage)
        // }
        $scope.findPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage)
    }
    /**
     * 当前页 品牌列表
     * @param page 当前页
     * @param rows 每页大小
     */
    $scope.findPage=function (page,rows) {
        $http.get('../brand/findPage.do?page='+page+'&rows='+rows).success(
            function(response){
                $scope.brandList=response.rows;
                $scope.paginationConf.totalItems=response.total;
            }
        );
    }

    /**
     * 增加一条 品牌列表
     */
    $scope.save=function(){
        var methodName="add"
        if($scope.brand.id!=null){
            methodName="update"
        }
        $http.post('../brand/'+methodName+'.do',$scope.brand ).success(
            function(response){
                if(response.success){
                    //重新加载
                    $scope.reloadList();
                }else{
                    alert(response.message);
                }
            }
        );
    }
    /**
     * 获得一条数据
     */
    $scope.findOne=function(id){
        $http.post('../brand/findOne.do?id='+id ).success(
            function(response){
                $scope.brand=response
            }
        );
    }

    $scope.selectIds=[]
    /**
     * 复选框
     * @param $event
     * @param id
     */
    $scope.updateSelection=function($event,id){
        if($event.target.checked){
            $scope.selectIds.push(id)
        }else{
            var idx = $scope.selectIds.indexOf(id);
            $scope.selectIds.splice(idx, 1);
        }
    }
    /**
     * 删除 品牌列表
     */
    $scope.delete=function(){
        $http.post('../brand/delete.do?ids='+$scope.selectIds).success(
            function(response){
                if(response.success){
                    //重新加载
                    $scope.reloadList();
                }else{
                    alert(response.message);
                }
            }
        );
    }
    
    
    $scope.searchBrand={}
    $scope.search=function (page,rows) {
        $http.post('../brand/search.do?page='+page+"&rows="+rows, $scope.searchBrand).success(
            function(response){
                $scope.paginationConf.totalItems=response.total;//总记录数
                $scope.list=response.rows;//给列表变量赋值
            }
        );
    }
});
