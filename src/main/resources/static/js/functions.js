/** Create js functions here*/
function filter(header, token) {
    var search = {}
    search["dateFromFilterStr"]        = $("#dateFromFilter").val();
    search["dateToFilterStr"]          = $("#dateToFilter").val();
    search["roomFilterStr"]            = $("#roomFilter").val();
    search["dateFromArrivalFilterStr"]     = $("#dateFromArrivalFilter").val();
    search["dateFromDepartureFilterStr"]   = $("#dateFromDepartureFilter").val();
    search["dateToArrivalFilterStr"]     = $("#dateToArrivalFilter").val();
    search["dateToDepartureFilterStr"]   = $("#dateToDepartureFilter").val();
    search["adminFilterStr"]             = $("#adminFilter").val();



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

        beforeSend: function(xhr){xhr.setRequestHeader(header, token);},

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
                var cellAdmins          = row.insertCell(5);
                var cellActions         = row.insertCell(6);

                cellbookingId.innerHTML     = bookings[i].id;
                cellDate.innerHTML          = new Date(bookings[i].dateBuking).toLocaleString("ru", options);
                cellRoom.innerHTML          = bookings[i].room.no;
                cellArrivalDate.innerHTML   = new Date(bookings[i].arrivalDate).toLocaleString("ru", options);
                cellDateDeparture.innerHTML = new Date(bookings[i].dateOfDeparture).toLocaleString("ru", options);
                cellAdmins.innerHTML        = bookings[i].user.username;

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

function getRoomsFromServerApi(header, token) {
    var arrivalDate   = $("#dateFromArrivalFilter").val();
    var departureDate = $("#dateFromDepartureFilter").val();
    var roomType = $("#roomTypeFilter").val();

    var roomDiv = $("#roomSelectDiv");
    var warnDiv = $("#warningDiv");


    if (arrivalDate == '' || departureDate == '' || roomType == '') {
        roomDiv.hide();
        warnDiv.show();
        return false ;
    }

    roomDiv.show();
    warnDiv.hide();

    var search = {}
    search["dateFromArrivalFilterStr"]     = arrivalDate;
    search["dateFromDepartureFilterStr"]   = departureDate;
    search["roomTypeFilterStr"]   = roomType;

    $.ajax({
        type: "POST",
        url: "/booking/api/roomFilter",
        data: JSON.stringify(search),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        dataType: 'json',
        cache: false,
        timeout: 600000,

        beforeSend: function(xhr){ xhr.setRequestHeader(header, token);},

        success: function (data) {
            updateRooms(data.rooms);
        }
    })
}

function updateRooms(rooms) {
    /*<![CDATA[*/

    var options = "<option value='' selected='selected' >Select to Filter..</option>";
    for (var i = 0; i < rooms.length; i++) {
        options += "<option value='"+rooms[i].id+"' text='"+rooms[i].no+"'>"+rooms[i].no+"</option>"
    }
    $('#rooms').html(options);
}