class WSclient {
    ws = null;
    parameters = null;

    constructor(parameters) {
        this.parameters = parameters;
        if (!this.parameters['path']) {
            return;
        }

        this.reconnect();
    }

    sendMessage(json) {
        console.log('sending', json);
        this.ws.send(JSON.stringify(json));
    }

    _parseJson(event) {
        console.log('received', event.data);
        const parsed = JSON.parse(event.data);
        this.parameters['onmessage'](parsed);
    }

    _bindEvents() {
        if (this.parameters.onopen) {
            this.ws.addEventListener('open', this.parameters.onopen);
        }

        if (this.parameters.onmessage) {
            this.ws.addEventListener('message', function (event) {
                const json = JSON.parse(event.data);
                console.log('ws', this.ws);
                console.log('event', event);
                event.target.dispatchEvent(new CustomEvent('message-json', {
                    detail: {
                        data: json,
                        raw_message: event.data
                    }
                }));
            });
            this.ws.addEventListener('message-json', this.parameters.onmessage);
        }
        if (this.parameters['onclose']) {
            this.ws.addEventListener('close', this.parameters['onclose']);
        }
    }

    reconnect() {
        const host = window.location.host;
        const path = this.parameters['path'];
        this.ws = new WebSocket(`ws://${host}/${path}`);
        this._bindEvents();
    }

}

//
//
// function connect(reconnection = false) {
//     if (reconnection) {
//         ws = new WebSocket(`ws://${host}/chat`);
//     }
//
//     ws.addEventListener('open', (event) => {
//         //ws.send('Hello Server!');
//     });
//
//     // Listen for messages
//     ws.addEventListener('message', (event) => {
//         console.log('Message from server ', event.data);
//         const strMessage = event.data;
//         const objMessage = JSON.parse(strMessage);
//         if (!objMessage) {
//             return;
//         }
//         handleMessage(objMessage);
//         //document.getElementById('output-text-area').append(event.data);
//     });
//     ws.addEventListener('close', () => {
//         console.log('Reconnected');
//         connect(true);
//     });
// }
// function uuidv4() {
//     return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, c =>
//         (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
//     );
// }
// connect();