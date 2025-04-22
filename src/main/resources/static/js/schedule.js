const MAX_HOUR_VALUE = "23";
const MAX_MINUTE_VALUE = "59";
const MIN_TIME_VALUE = "0";

let timeFormsCounter = 1;


const scheduleTable = document.getElementById('schedule');


const editButton = document.getElementById('editButton');
const saveButton = document.getElementById('saveButton');

const plusButtonTime = document.createElement('button');
const minusButtonTime = document.createElement('button');

const plusButtonDay = document.createElement('button');
const minusButtonDay = document.createElement('button');

function addSpinners(currCell, spinnerH, spinnerM) {
    const container = document.createElement('div');
    container.style.display = 'flex';
    container.style.alignItems = 'center';

    container.appendChild(spinnerH);
    container.appendChild(spinnerM);

    currCell.innerHTML = "";
    currCell.appendChild(container);
}

function addForm(currCell) {
    const form = document.createElement("form");
    form.action = "";
    form.method = "put";
    const input = document.createElement("input");
    input.type = "text";
    input.value = "";
    form.appendChild(input);
    currCell.appendChild(form);
}

function startEditing() {
    saveButton.style.display = "block";
    plusButtonTime.style.display = "inline";
    minusButtonTime.style.display = "inline";
    plusButtonDay.style.display = "inline";
    minusButtonDay.style.display = "inline";
    editButton.style.display = "none"

    const table = document.getElementById('schedule');

    let currCell;
    for (let i = 1; i < table.rows.length; i++) {
        for (let j = 0; j < 2; j++) {
            currCell = table.rows[i].cells[j];

            const hour = currCell.innerHTML.split(":")[0];
            const minute = currCell.innerHTML.split(":")[1];

            const spinnerH = createHourSpinner();
            const spinnerM = createMinSpinner();


            spinnerH.value = hour;
            spinnerM.value = minute;


            addSpinners(currCell, spinnerH, spinnerM);

        }
    }


    for (let i = 1; i < table.rows.length; i++) {
        for (let j = 2; j < table.rows[0].cells.length - 1; j++) {
            currCell = table.rows[i].cells[j];
            const form = document.createElement("form");
            form.action = "";
            form.method = "put";
            const input = document.createElement("input");
            input.type = "text";
            input.value = currCell.innerHTML;

            input.maxLength = 50;
            input.addEventListener('input', function () {
                this.value = this.value.replace(/[\r\n]/g, '');
            });

            form.appendChild(input);
            currCell.innerHTML = "";
            currCell.appendChild(form);
        }

    }


    plusButtonTime.textContent = '+';
    editButton.insertAdjacentElement('afterend', plusButtonTime);


    minusButtonTime.textContent = '-';
    plusButtonTime.insertAdjacentElement('afterend', minusButtonTime);


    plusButtonDay.textContent = '+';


    const lastCellIndex = table.rows[0].cells.length - 1;
    const cell = table.rows[0].cells[lastCellIndex];
    cell.appendChild(plusButtonDay);

    if (defaultDays.length === 7) {
        plusButtonDay.style.display = "none";
    }

    if (scheduleTable.rows.length <= 2) {
        minusButtonTime.style.display = "none";
    }


    minusButtonDay.setAttribute('id', 'minusButtonDay');
    minusButtonDay.textContent = '-';
    plusButtonDay.insertAdjacentElement('afterend', minusButtonDay);

    if (scheduleTable.rows[0].cells.length < 5) {
        minusButtonDay.style.display = "none";
    }

    plusButtonTime.addEventListener('click', addTimeRow);
    minusButtonTime.addEventListener('click', removeTimeRow);

    plusButtonDay.addEventListener('click', addDay);
    minusButtonDay.addEventListener('click', removeDay);

    let inputForms = document.querySelectorAll('input[type="text"]');
    for (let i = 0; i < inputForms.length; i++) {
        inputForms[i].addEventListener("keydown", function (event) {
            if (event.key === "Enter") {
                event.preventDefault();
            }
        });
    }
}


editButton.addEventListener('click', startEditing);

function addTimeRow() {

    const table = document.getElementById("schedule");
    const message = document.getElementById("stateMessage");


    const auxH = document.getElementById("spinner" + (timeFormsCounter - 2)).value;
    const auxM = document.getElementById("spinner" + (timeFormsCounter - 1)).value;

    if (auxH === parseInt(MAX_HOUR_VALUE)) {
        if (auxM === parseInt(MAX_MINUTE_VALUE)) {
            message.style.color = "#8B0000";
            message.innerHTML = "Обратите внимание! Дальнейшее добавление строк невозможно, достигнут конец суток! Пожалуйста, отредактируйте имеющиеся строки и повторите попытку";
            return;
        }
    }


    const spinnerH1 = createHourSpinner();
    const spinnerM1 = createMinSpinner();
    const spinnerH2 = createHourSpinner();
    const spinnerM2 = createMinSpinner();


    if (auxH === parseInt(MAX_HOUR_VALUE)) {
        spinnerH1.value = MAX_HOUR_VALUE;
        if (auxM === parseInt(MIN_TIME_VALUE) || auxM < parseInt(MAX_MINUTE_VALUE)) {
            spinnerM1.value = (parseInt(auxM) + 1).toString().padStart(2, '0');
        } else {
            message.style.color = "#8B0000";
            message.innerHTML = "Обратите внимание! Дальнейшее добавление строк невозможно, достигнут конец суток! Пожалуйста, отредактируйте имеющиеся строки и повторите попытку";
            return;
        }
    } else {
        spinnerH1.value = (parseInt(auxH) + 1).toString().padStart(2, '0');
        spinnerM1.value = MIN_TIME_VALUE.padStart(2, '0');
    }


    if (spinnerH1.value < parseInt(MAX_HOUR_VALUE)) {
        spinnerH2.value = (parseInt(auxH) + 2).toString().padStart(2, '0');
        spinnerM2.value = MIN_TIME_VALUE.padStart(2, '0');
    } else {
        spinnerH2.value = MAX_HOUR_VALUE.padStart(2, '0');
        spinnerM2.value = MAX_MINUTE_VALUE.padStart(2, '0');
    }


    let newRow = table.insertRow();


    for (let i = 0; i < table.rows[0].cells.length - 1; i++) {
        let currCell = newRow.insertCell();
        if (i < 2) {
            let spinnerH, spinnerM;
            if (i === 0) {
                spinnerH = spinnerH1;
                spinnerM = spinnerM1;
            } else {
                spinnerH = spinnerH2;
                spinnerM = spinnerM2;
            }

            addSpinners(currCell, spinnerH, spinnerM);
        } else {
            addForm(currCell);
        }
    }

    if (minusButtonTime.style.display === "none") {
        minusButtonTime.style.display = "inline";
    }
}

function removeTimeRow() {
    const table = document.getElementById('schedule');
    const rows = table.getElementsByTagName('tr');
    const message = document.getElementById('stateMessage');
    table.deleteRow(rows.length - 1);
    timeFormsCounter = timeFormsCounter - 4;
    if (table.rows.length < 3) {
        minusButtonTime.style.display = "none";
    }
    if (message.innerHTML ===
        "Обратите внимание! Дальнейшее добавление строк невозможно, достигнут конец суток! Пожалуйста, отредактируйте имеющиеся строки и повторите попытку") {
        message.innerHTML = "";
    }
}


function addDay() {
    const table = document.getElementById("schedule");
    const presentDaysAm = table.rows[0].cells.length - 3;

    for (let i = 0; i < table.rows.length; i++) {
        let currCell;
        if (i === 0) {

            const headerRow = table.rows[0];
            const cellValue = allDays[presentDaysAm].toString();
            currCell = headerRow.insertCell(presentDaysAm + 2);
            currCell.style.background = "#99ccff";
            currCell.innerHTML = cellValue;
            currCell.style.fontWeight = "bold";
        } else {
            currCell = table.rows[i].insertCell(presentDaysAm + 2);
            addForm(currCell);
        }
    }
    if (table.rows[0].cells.length === 10) {
        plusButtonDay.style.display = "none";
        minusButtonDay.style.display = "inline";
    } else if (table.rows[0].cells.length > 4) {
        plusButtonDay.style.display = "inline";
        minusButtonDay.style.display = "inline";
    }
}

function removeDay() {
    const table = document.getElementById("schedule");
    const rowsAmount = table.rows.length;
    for (let i = 0; i < rowsAmount; i++) {
        table.rows[i].deleteCell(table.rows[i].cells.length - 2);
    }


    if (table.rows[0].cells.length === 4) {
        plusButtonDay.style.display = "inline";
        minusButtonDay.style.display = "none";
    } else if (table.rows[0].cells.length < 10) {
        plusButtonDay.style.display = "inline";
        minusButtonDay.style.display = "inline";
    }
}


function saveChanges() {
    const table = document.getElementById("schedule");
    const timesLength = table.rows.length - 1;
    const message = document.getElementById("stateMessage");

    let newStartTimes = [];
    let newEndTimes = [];
    let newDays = [];
    let timeFormsCounter = 1;
    for (let i = 1; i < table.rows.length; i++) {
        let initRes = initNewTimeArrElement(timeFormsCounter);
        let newStartTime = initRes[0];
        timeFormsCounter = initRes[1];

        initRes = initNewTimeArrElement(timeFormsCounter);
        let newEndTime = initRes[0];
        timeFormsCounter = initRes[1];


        newStartTimes.push(newStartTime);
        newEndTimes.push(newEndTime);

    }
    for (let i = 2; i < table.rows[0].cells.length - 1; i++) {
        newDays.push(table.rows[0].cells[i].innerHTML);
    }
    for (let i = 0; i < timesLength; i++) {
        if (!checkTime(newStartTimes, newEndTimes)) {
            message.style.color = "#8B0000";
            message.innerHTML = "Не удалось сохранить расписание! Проверьте правильность введенного времени.";
            return;
        }
    }



    for (let i = 0; i < defaultStartTimes.length; i++) {
        if (!(newStartTimes.includes(defaultStartTimes[i])) || !(newEndTimes.includes(defaultEndTimes[i]))) {
            let toDelete = {
                id: {
                    startTime: defaultStartTimes[i],
                    endTime: defaultEndTimes[i]
                }
            };

            fetch('/schedule/' + userId, {
                method: 'DELETE',
                headers: {
                    'Content-type': 'application/json',
                },
                body: JSON.stringify(toDelete)
            }).then(data => {
            })
                .catch(error => {
                    console.error('Ошибка:', error);
                });
        }
    }


    for (let i = 0; i < defaultDays.length; i++) {
        if (!(newDays.includes(defaultDays[i]))) {
            let toDelete = {
                id: {
                    day: defaultDays[i]
                }
            };
            fetch('/schedule/' + userId, {
                method: 'DELETE',
                headers: {
                    'Content-type': 'application/json',
                },
                body: JSON.stringify(toDelete)
            }).then(data => {
            })
                .catch(error => {
                    console.error('Ошибка:', error);
                });
            defaultDays.splice(i, 1);
        }
    }



    for (let i = 0; i < timesLength; i++) {
        const currStartTime = newStartTimes[i];
        const currEndTime = newEndTimes[i];

        if (defaultStartTimes.includes(currStartTime) && defaultEndTimes.includes(currEndTime)) {
            for (let j = 0; j < newDays.length; j++) {
                if (defaultDays.includes(newDays[j])) {
                    let newCell = {
                        id: {
                            startTime: currStartTime,
                            endTime: currEndTime,
                            day: newDays[j]
                        },
                        content: table.rows[i + 1].cells[j + 2].querySelector('input').value,
                        userId: userId
                    };
                    fetch('/schedule/' + userId, {
                        method: 'PUT',
                        headers: {
                            'Content-type': 'application/json',
                        },
                        body: JSON.stringify(newCell)
                    }).then(data => {
                    })
                        .catch(error => {
                            console.error('Ошибка:', error);
                        });
                } else {
                    let newCell = {
                        id: {
                            startTime: currStartTime,
                            endTime: currEndTime,
                            day: newDays[j]
                        },
                        content: table.rows[i + 1].cells[j + 2].querySelector('input').value,
                        userId: userId
                    };
                    fetch('/schedule/' + userId, {
                        method: 'POST',
                        headers: {
                            'Content-type': 'application/json',
                        },
                        body: JSON.stringify(newCell)
                    }).then(data => {
                    })
                        .catch(error => {
                            console.error('Ошибка:', error);
                        });
                }

            }
        } else {
            for (let j = 0; j < newDays.length; j++) {
                let newCell = {
                    id: {
                        startTime: currStartTime,
                        endTime: currEndTime,
                        day: newDays[j]
                    },
                    content: table.rows[i + 1].cells[j + 2].querySelector('input').value,
                    userId: userId
                };
                fetch('/schedule/' + userId, {
                    method: 'POST',
                    headers: {
                        'Content-type': 'application/json',
                    },
                    body: JSON.stringify(newCell)
                }).then(data => {
                })
                    .catch(error => {
                        console.error('Ошибка:', error);
                    });
            }
        }
    }
    saveChanges_interface(newStartTimes, newEndTimes);
}

function initNewTimeArrElement(timeFormsCounter) {
    let newTime = document.getElementById("spinner" + timeFormsCounter).value;

    timeFormsCounter++;
    newTime = newTime + ":";
    newTime = newTime + document.getElementById("spinner" + timeFormsCounter).value;
    newTime = newTime + ":00";
    timeFormsCounter++;
    return [newTime, timeFormsCounter];
}

function saveChanges_interface(startTimes, endTimes) {
    const table = document.getElementById("schedule");


    for (let i = 1; i < table.rows.length; i++) {
        for (let j = 0; j < table.rows[0].cells.length - 1; j++) {
            if (j === 0) {
                table.rows[i].cells[j].innerHTML = startTimes[i - 1].split(":")[0] + ":" + startTimes[i - 1].split(":")[1];
            } else if (j === 1) {
                table.rows[i].cells[j].innerHTML = endTimes[i - 1].split(":")[0] + ":" + endTimes[i - 1].split(":")[1];

            } else {
                table.rows[i].cells[j].innerHTML = table.rows[i].cells[j].querySelector('input').value;
            }
            (table.rows[i].cells[j].innerHTML)
        }
    }


    plusButtonTime.style.display = "none";
    minusButtonTime.style.display = "none";
    plusButtonDay.style.display = "none";
    minusButtonDay.style.display = "none";
    saveButton.style.display = "none";
    editButton.style.display = "inline"
}

function createHourSpinner() {
    const input = document.createElement("input");
    input.type = "number";
    input.min = "0";
    input.max = "23";
    input.step = "1";
    input.id = "spinner" + timeFormsCounter;
    timeFormsCounter++;

    return input;
}

function createMinSpinner() {
    const input = document.createElement("input");
    input.type = "number";
    input.min = "0";
    input.max = "59";
    input.step = "1";
    input.id = "spinner" + timeFormsCounter;
    timeFormsCounter++;

    return input;
}

function checkTime(startTimes, endTimes) {
    for (let i = 0; i < startTimes.length; i++) {
        const startHour = startTimes[i].split(":")[0];
        const startMinute = startTimes[i].split(":")[1];

        const endHour = endTimes[i].split(":")[0];
        const endMinute = endTimes[i].split(":")[1];


        if (startHour >= endHour) {
            if (startHour === endHour && startMinute >= endMinute) {
                return false;
            } else if (startHour > endHour) {
                return false;
            }
        }
        if (i > 0) {
            const endHour1 = endTimes[i - 1].split(":")[0];
            const endMinute1 = endTimes[i - 1].split(":")[1];
            if (startHour <= endHour1) {
                if (startHour === endHour1 && startMinute >= endMinute1) {
                    return false;
                } else if (startHour < endHour1) {
                    return false;
                }
            }
        }
    }
    return true;
}

document.getElementById('saveButton').addEventListener('click', saveChanges)