@Grab(group='org.twitter4j', module='twitter4j-core', version='4.0.6')

import java.util.Calendar
import twitter4j.TwitterFactory
import twitter4j.StatusUpdate
import java.util.Locale
import java.text.SimpleDateFormat


today = Calendar.instance

videos = [
    "https://youtu.be/4EIGbQaEIN0",
    "https://youtu.be/1AnG04qnLqI",
    "https://youtu.be/U_44YUDFvuI",
    "https://youtu.be/Wm4YqE2XQwM",
    "https://youtu.be/MVkDDSz_gE4",
    "https://youtu.be/UDPUPJUumXE",    
]

losdemarras = "https://youtu.be/Iu1bKskocNQ"

txt = ''
video = ''
if( today.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY ){
    txt = 'Hoy sí que es viernes, salimos a las 3 y NO hay paso a producción'
    video = losdemarras
}else{
    txt = "Vale, hoy a lo mejor no es viernes, ni salimos a las 3 pero NO hay paso a producción"
    rnd = new Random()
    video = videos[ rnd.nextInt(videos.size()) ]
}

message = ""
['es-ES':"Hoy ", 
 'en-GB':'Today ', 
 'fr-FR': "aujourd'hui ", 
 'zh-CN':"今天 ",
 'ru-RU':"сегодня "].each{ kv ->
    String l = kv.key    
    Locale locale = Locale.forLanguageTag(l)
    
    message += kv.value+SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG, locale).format(today.time)
    message += "\n"
}
message += "\n"
message += txt
message += "\n\n"
message += video

status = new StatusUpdate(message)
if( args.length > 1 ){
    def bytes = args[1].toURL().bytes
    status.media "image", new ByteArrayInputStream(bytes)
}
println status.status
TwitterFactory.singleton.updateStatus(status)

