var dgram = require('dgram')
var fs = require('fs')
var os = require('os')
var ssdpAddress = '239.255.255.250'
var ssdpPort = 1900
var sourcePort = 0                 // chosen at random
var searchTarget  = 'upnp:rootdevice' 
//var searchTarget  = 'ssdp:all' 
var socket
var interfaces = os.networkInterfaces()
for (var k in interfaces) {
	for (var k2 in interfaces[k]) {
		var address = interfaces[k][k2]
		if (address.family === 'IPv4' && !address.internal) {
			var sourceIface = address.address
		}
	}
}
createSocket()
//	-------------------------------------------------------------------
function createSocket() {
	socket = dgram.createSocket('udp4')
	socket.on('listening', function () {
		console.log('socket ready...')
		broadcastSsdp()
	});
	socket.on('message', function (chunk, info) {
		var message = chunk.toString();
		console.log('[incoming] UDP message')
		console.log(info)
		console.log(message)
		fs.appendFile("UpnpDiscoveryLog.txt", info)
		fs.appendFile("UpnpDiscoveryLog.txt", message)
	})
	console.log('binding to', sourceIface + ':' + sourcePort)
	socket.bind(sourcePort, sourceIface);
}
//	-------------------------------------------------------------------
function broadcastSsdp() {
	var query = new Buffer(
		'M-SEARCH * HTTP/1.1\r\n'
		+ 'HOST: ' + ssdpAddress + ':' + ssdpPort + '\r\n'
		+ 'MAN: "ssdp:discover"\r\n'
		+ 'MX: 1\r\n'
		+ 'ST: ' + searchTarget + '\r\n'
		+ '\r\n'
	)
	socket.send(query, 0, query.length, ssdpPort, ssdpAddress)
}
//	-------------------------------------------------------------------
function encrypt (input) {
	var buf = Buffer.alloc(input.length); // node v6: Buffer.alloc(input.length)
	var key = 0xAB
	for (var i = 0; i < input.length; i++) {
		buf[i] = input.charCodeAt(i) ^ key
		key = buf[i]
	}
	var bufLength = Buffer.alloc(4); // node v6: Buffer.alloc(4)
	bufLength.writeUInt32BE(input.length, 0)
	return Buffer.concat([bufLength, buf], input.length + 4)
}
//	-------------------------------------------------------------------
function decrypt (input, firstKey) {
	var buf = Buffer.from(input); // node v6: Buffer.from(input)
	var key = 0x2B
	var nextKey
	for (var i = 0; i < buf.length; i++) {
		nextKey = buf[i]
		buf[i] = buf[i] ^ key
		key = nextKey
	}
	return buf
}
