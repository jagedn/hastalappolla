@Grab(group='org.twitter4j', module='twitter4j-core', version='4.0.6')

import twitter4j.TwitterFactory
import twitter4j.StatusUpdate
import java.util.Locale
import java.text.SimpleDateFormat

if( args.length < 1){
   println "nasty"
   return
}

today = new Date()

message = ""
['es-ES':"Hoy ", 
 'en-GB':'Today ', 
 'fr-FR': "aujourd'hui ", 
 'zh-CN':"今天 ",
 'ru-RU':"сегодня "].each{ kv ->
    String l = kv.key    
    Locale locale = Locale.forLanguageTag(l)
    
    message += kv.value+SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG, locale).format(today)
    message += "\n"
}
message += "\n"
message += args[0]
//message += "\n"
//message += today.day % 2 == 0 ? "OLE https://youtu.be/4EIGbQaEIN0" : "MUFASA https://youtu.be/1AnG04qnLqI"

status = new StatusUpdate(message)
if( args.length > 1 ){
    def bytes = args[1].toURL().bytes
    status.media "image", new ByteArrayInputStream(bytes)
}
println status.status
TwitterFactory.singleton.updateStatus(status)

