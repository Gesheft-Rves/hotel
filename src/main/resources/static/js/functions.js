/** Create js functions here*/
function filter() {
    var search = {}
    search["dateFromFilterStr"]        = $("#dateFromFilter").val();
    search["dateToFilterStr"]          = $("#dateToFilter").val();
    search["roomFilterStr"]            = $("#roomFilter").val();
    search["dateArrivalFilterStr"]     = $("#dateArrivalFilter").val();
    search["dateDepartureFilterStr"]   = $("#dateDepartureFilter").val();
    search["dateArrivalFilterStr2"]     = $("#dateArrivalFilter2").val();
    search["dateDepartureFilterStr2"]   = $("#dateDepartureFilter2").val();



    $.ajax({
        type: "POST",
        url: "/booking/api/filter",
        data: JSON.stringify(search),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        dataType: 'json',
        cache: false,
        timeout: 600000,

        success: function (data) {

            var bookings = data.bookings;
            var table = document.getElementById("bookingsTable");
            var options = {
                year:  'numeric',
                month: 'numeric',
                day:   'numeric',
                hour:  'numeric',
                minute:'numeric',
            };

            while (table.rows.length > 2){
                table.deleteRow(2);
            }

            var size = bookings.length;
            for (var i = 0; i < size; i++){

                // вставляет строку
                var row = table.insertRow(2);

                // вставляет ячейки
                var cellbookingId       = row.insertCell(0);
                var cellDate            = row.insertCell(1);
                var cellRoom            = row.insertCell(2);
                var cellArrivalDate     = row.insertCell(3);
                var cellDateDeparture   = row.insertCell(4);
                var cellActions         = row.insertCell(5);

                cellbookingId.innerHTML     = bookings[i].id;
                cellDate.innerHTML          = new Date(bookings[i].date_buking).toLocaleString("ru", options);
                cellRoom.innerHTML          = bookings[i].room.no;
                cellArrivalDate.innerHTML   = bookings[i].arrival_date;
                cellDateDeparture.innerHTML = bookings[i].date_of_departure;

                var id = bookings[i].id
                urlInCell(cellActions, "edit", id);
                urlInCell(cellActions, "details", id);
                urlInCell(cellActions,"delete", id);
            }
        },
        error: function (e) {
            alert("error:"+e);
        }
    });

    function urlInCell(cell, action, id) {
        var url = "http://" + window.location.host + "/booking/" + action + "/" + id;

        var createA = document.createElement('a');
        var createAText = document.createTextNode(action);
        createA.setAttribute('href', url);
        createA.appendChild(createAText);
        cell.appendChild(createA);

        var space = document.createTextNode(' ');
        cell.appendChild(space);
    }
}

