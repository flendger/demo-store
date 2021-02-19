angular.module('app').controller('cardController', function ($scope, $http, $location, $routeParams) {
    const contextPath = 'http://localhost:8189/store';
    let msgTxt = "";

    $scope.fillComments = function () {
        $http.get(contextPath + "/api/v1/comments/" + $routeParams.id)
            .then(function (response) {
                $scope.commentList = response.data;
            });
    }

    $scope.placeNewComment = function () {
        $scope.newComment.productDto = $scope.product;
        $http.post(contextPath + "/api/v1/comments", $scope.newComment)
            .then(function () {
                $scope.newComment = null;
                $scope.fillComments();
            });
    }

    $('#infoModal').on('show.bs.modal', function () {
        const modal = $(this);
        modal.find('.info-text').text(msgTxt)
    })

    $('#myTab a').on('click', function (event) {
        event.preventDefault()
        $(this).tab('show')
    });

    $http.get(contextPath + "/api/v1/products/" + $routeParams.id)
        .then(function (response) {
            $scope.product = response.data;
        });

    $scope.fillComments();
});