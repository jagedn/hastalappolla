@Grab(group='org.twitter4j', module='twitter4j-core', version='4.0.6')
@Grab('org.ccil.cowan.tagsoup:tagsoup:1.2.1')
import org.ccil.cowan.tagsoup.Parser

import twitter4j.TwitterFactory
import twitter4j.StatusUpdate
import java.util.Locale
import java.text.SimpleDateFormat

html = "https://es.wikiquote.org/wiki/Especial:Aleatoria".toURL().text 
slurper = new XmlSlurper(new Parser() )
document = slurper.parseText(html)

canonical = document.'**'.find{ it['@rel']  == 'canonical'}['@href']

quotes = document.'**'.find { it['@class'] == 'mw-parser-output' }.ul

rand = new Random()
quote = quotes[ rand.nextInt(quotes.size())]
text = quote.children().first().text().substring(1).split('»').first().take(150)


status = new StatusUpdate("""» $text
($canonical)
#wikiquote""")

println status.status
TwitterFactory.singleton.updateStatus(status)

