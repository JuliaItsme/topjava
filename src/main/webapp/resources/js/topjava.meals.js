const mealAjaxUrl = "profile/meals/";

$('.datetimepicker').datetimepicker({
    format: 'Y-m-d H:i'
});

$('.datepicker').datetimepicker({
    timepicker: false,
    format: 'Y-m-d'
});

$('.timepicker').datetimepicker({
    datepicker: false,
    format: 'H:i'
});

$.ajaxSetup({
    converters: {
        "text json_date": function (stringData) {
            let jsonDate = JSON.parse(stringData);
            $(jsonDate).each(function () {
                this.dateTime = this.dateTime.replace('T', ' ').substr(0, 16);
            });
            return jsonDate;
        }
    }
});

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        dataType: 'json_date',
        url: mealAjaxUrl + 'filter',
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: mealAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                url: mealAjaxUrl,
                dataType: 'json_date',
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "",
                    "orderable": false,
                    "render": renderEditBtn
                },
                {
                    "defaultContent": "",
                    "orderable": false,
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                $(row).attr("data-mealExcess", data.excess);
            }
        }),
        updateTable: updateFilteredTable
    });
});