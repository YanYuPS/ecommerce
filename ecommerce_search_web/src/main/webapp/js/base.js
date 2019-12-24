var app=angular.module('ecommerce',[])

//用$sce服务的trustAsHtml方法，保证设置的高亮效果能够展示出来
/*$sce服务写成过滤器*/
app.filter('trustHtml',['$sce',function($sce){
    return function(data){
        return $sce.trustAsHtml(data);
    }
}]);
