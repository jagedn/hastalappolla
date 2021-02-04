@Grab(group='org.twitter4j', module='twitter4j-core', version='4.0.6')

import twitter4j.TwitterFactory
import twitter4j.StatusUpdate
import java.util.Locale
import java.text.SimpleDateFormat


status = new StatusUpdate("Aquí tienes tu magdalena diaria @ciberado, que aproveche")
files = new File("images").listFiles()
rnd = new Random()
file = files[rnd.nextInt(files.length)]
bytes = file.bytes
status.inReplyToStatusId 1356670780554305543L
status.media "image", new ByteArrayInputStream(bytes)
println status.status
TwitterFactory.singleton.updateStatus(status)

