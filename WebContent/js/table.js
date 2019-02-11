function formatVictoire(value, row, index) {
    return value == 0 ? 'Défaite' : 'Victoire';
}

function formatDate(value, row, index) {
    return moment(value, 'MMM D, YYYY h:m:s a').format('YYYY-MM-DD HH:mm:ss');
}

function initTablePer(classname, url, pseudo) {
    $(classname).bootstrapTable({
        url: url,
        queryParams: function (p) {
            return {
                pseudo: pseudo
            };
        },
        pagination: true,
        search: true,
        showExport: true,
        columns: [{
            field: 'score',
            sortable: true,
            title: 'Score'
        }, {
            field: 'victoire',
            sortable: true,
            formatter: formatVictoire,
            title: 'Victoire'
        }, {
            field: 'date',
            sortable: true,
            formatter: formatDate,
            title: 'Date'
        }, ]
    })
}

function initTableGen(classname, url) {
    $(classname).bootstrapTable({
        url: url,
        pagination: true,
        search: true,
        showExport: true,
        columns: [{
            field: 'score',
            sortable: true,
            title: 'Score'
        }, {
            field: 'pseudo',
            sortable: true,
            title: 'Pseudo'
        }, {
            field: 'victoire',
            sortable: true,
            formatter: formatVictoire,
            title: 'Victoire'
        }, {
            field: 'date',
            sortable: true,
            formatter: formatDate,
            title: 'Date'
        }, ]
    })
}