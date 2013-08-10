Hierarchy Adapter Kata
=============

TLDR;
---------
Make the acceptance tests pass :)

**You may not change the service json formats**

**You may not change the persistent json formats (at least the first time you do the kata)**

System description
---------
The code represents a fake REST server which exposes 3 apis, 

* `POST /events`
    
    Create a new event based on the provided json payload. The event id is optional and will be set by the server if it isn't provided. The returned payload represents the full event and always has an id.

* `GET  /events/:id`

    Read the event which has the id `:id`

* `GET  /events/xmpp/:id`
    
    Read the event which has the xmppid `:id`

The Json formats for the payloads are detailed at the end of this explantion.

Business constraints 
--------
Milky Way Corporation has recently bought Flying Unicorn, a startup specialized in handling events which was competing with MWC's existing product. The startup product is much more successful and is used by millions of people around the world to read and store events. 

However, MWC's owner, Sir Evil Madman, isn't impressed with the backend solution of Flying Unicorn. Contrary to that of MWC's it has never been built with world domination in mind. He wants all events written in MWC's backend. The  World Dominator backend is a legacy system written in [brainfuck](http://en.wikipedia.org/wiki/Brainfuck), whose original developer was killed by a meteorite while in her pool with her husband (at least that's the official version, the house and the pool having been completely destroyed by the explosion). 

You have been tasked to complete a translation system used to expose events to and from World Dominator. The intern who was tasked to do so before you only got as far as doing a jackson mapping on objects and writing a stubbed resource exposing the 3 apis before a spider bit him and he died an horribly painful death in his dumpster. You need to finish the project it as soon as possible, the HR department's friends at NASA discovered that a new meteorite is on a collision course with _your_ house.

The Architect provides a company-wide standard router (which is compatible with the intern's resource),the event store and the ultrasecure cryptographic service used for filename storage. The cryptographic service you are provided with is a stub, The Architect is working on the real implementation which will involve a dozen webservice calls, high end encryption using native libraries, communication with the NSA, etc. The persistence support package also comes from the architect library and cannot be touched.

* You may not change the service json formats, they are used by millions of clients on the internet
* You may not change the persistence json formats, at least the first time you do the kata, consider that the database is shared by a legacy system for which the company has no developpers anymore

Exercise
-----------
Your goal is to implement the missing link between the resource and the store, making use of the CryptoService service when manipulating file or audio events.

Json formats
------------- 
### Service side
All service JSONs can have and additional id (only present for persisted events) which is an UUID string. 

Sms event
```json
{
    "type":"sms",
    "sender":"+33605040302",
    "sms":"coucou"
}
```
Text event
```json
{
    "type":"text",
    "xmppId":"XMPP001",
    "text":"message of text event"
}
```
File event
```json
{
    "type":"file",
    "xmppId":"XMPP002",
    "url":"file:/filename"
}
```
Audio  event
```json
{
    "type":"audio",
    "xmppId":"XMPP003",
    "url":"file:/filename",
    "duration":15
}
```

### Persistence

Sms Event
```json
{
    "type":"sms",
    "id":"...UUID...",
    "from":"+33605040302",
    "text":"coucou",
    "userId":1
}
```
Text Event
```json
{
    "type":"text",
    "id":"...UUID...",
    "message":"message of text event",
    "xmppId":"XMPP001",
    "userId":1
}
```
File Event
```json
{
    "type":"file",
    "id":"...UUID...",
    "xmppId":"XMPP002",
    "url":"file:/filename",
    "userId":1
}
```
Audio Event
```json
{
    "type":"audio",
    "id":"...UUID...",
    "xmppId":"XMPP003",
    "url":"file:/filename",
    "duration":15,
    "userId":1
}
```
