@Grab(group='org.twitter4j', module='twitter4j-core', version='4.0.6')
@Grab('org.ccil.cowan.tagsoup:tagsoup:1.2.1')
import org.ccil.cowan.tagsoup.Parser

import twitter4j.TwitterFactory
import twitter4j.StatusUpdate
import java.util.Locale
import java.text.SimpleDateFormat

html = "https://es.wikiquote.org/wiki/Portada".toURL().text 
def slurper = new XmlSlurper(new Parser() )
def document = slurper.parseText(html)
quote = document.'**'.find { it['@id'] == 'toc' }.table.tbody.'*'.findAll{ it.name() == 'tr'}.first().text()
author = document.'**'.find { it['@id'] == 'toc' }.table.tbody.'*'.findAll{ it.name() == 'tr'}.last().td.div.a.text()
link  = "https://es.wikiquote.org/"+document.'**'.find { it['@id'] == 'toc' }.table.tbody.'*'.findAll{ it.name() == 'tr'}.last().td.div.a['@href']

status = new StatusUpdate("""Aquí tienes tu magdalena diaria @ciberado, que aproveche

$quote
($author $link)

#wikiquote""")

files = new File("images").listFiles()
rnd = new Random()
file = files[rnd.nextInt(files.length)]
bytes = file.bytes
status.inReplyToStatusId 1356670780554305543L
status.media "image", new ByteArrayInputStream(bytes)
println status.status
TwitterFactory.singleton.updateStatus(status)

