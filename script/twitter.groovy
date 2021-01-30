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
['es-ES', 'en-GB','zh-CN','ru-RU','ar_BH'].each{ l ->
    Locale locale = Locale.forLanguageTag(l)

    message += SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG, locale).format(today)
    message += "\n"
}
message += "\n"
message += args[0]

status = new StatusUpdate(message).inReplyToStatusId(1355438500502523906L)
if( args.length > 1 ){
    def bytes = args[1].toURL().bytes
    status.media "image", new ByteArrayInputStream(bytes)
}
println status.status
TwitterFactory.singleton.updateStatus(status)

