@Grab(group='org.twitter4j', module='twitter4j-core', version='4.0.6')
@Grab('org.ccil.cowan.tagsoup:tagsoup:1.2.1')
@Grab('io.github.http-builder-ng:http-builder-ng-core:1.0.4')

import static groovyx.net.http.HttpBuilder.configure
import org.ccil.cowan.tagsoup.Parser

import twitter4j.TwitterFactory
import twitter4j.StatusUpdate
import java.util.Locale
import java.text.SimpleDateFormat

for(int i=0; i<3; i++){
    html = "https://es.wikiquote.org/wiki/Especial:Aleatoria".toURL().text 
    slurper = new XmlSlurper(new Parser() )
    document = slurper.parseText(html)

    personaje = document.'**'.find{ it['@id']  == 'firstHeading'}.text()

    canonical = document.'**'.find{ it['@rel']  == 'canonical'}['@href']

    quotes = document.'**'.find { it['@class'] == 'mw-parser-output' }.ul.findAll{ it.children().first().text().startsWith('«')}
    if( quotes.size() )
        break
}

rand = new Random()
quote = quotes[ rand.nextInt(quotes.size())]
text = quote.children().first().text().substring(1).split('»').first()
tweet = text.take(200)

status = new StatusUpdate("""» $tweet

$personaje ($canonical)
#wikiquote""")

println personaje
println text
println canonical

TwitterFactory.singleton.updateStatus(status)

TwitterFactory.singleton.updateProfile(null,null,null,tweet)

http = configure {
    request.uri = 'https://mastodon.madrid'
    request.contentType = 'application/json'
    request.headers.Authorization="Bearer ${System.getProperty('MASTODON_TOKEN')}"
}.post{
    request.uri.path="/api/v1/statuses"
    request.body=[
        status: """» $text

$personaje ($canonical)
#wikiquote"""
    ]
}
