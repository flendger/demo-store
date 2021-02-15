angular.module('app').controller('profileController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8189/store';
    let msgTxt = "";

    $scope.fillOrders = function () {
        $http.get(contextPath + "/api/v1/orders/user_orders")
            .then(function (response) {
                $scope.orderList = response.data;
            })
    }

    $('#infoModal').on('show.bs.modal', function () {
        const modal = $(this);
        modal.find('.info-text').text(msgTxt)
    })

    $scope.fillOrders();
});