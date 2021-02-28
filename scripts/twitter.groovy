@Grab(group='org.twitter4j', module='twitter4j-core', version='4.0.6')

import java.util.Calendar
import twitter4j.TwitterFactory
import twitter4j.StatusUpdate
import java.util.Locale
import java.text.SimpleDateFormat


today = Calendar.instance

losdemarras = "https://youtu.be/Iu1bKskocNQ"

txt = ''
video = ''
bytes = null
if( today.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY ){
    txt = 'Hoy sí que es viernes, salimos a las 3 y NO hay paso a producción'
    video = losdemarras
}else{
    files = new File("images/mojon").listFiles()
    rnd = new Random()
    file = files[rnd.nextInt(files.length)]
    bytes = file.bytes

    txt = "Tremendo mojon tienes por delante"    
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
if( bytes )
    status.media "image", new ByteArrayInputStream(bytes)

println status.status
TwitterFactory.singleton.updateStatus(status)

