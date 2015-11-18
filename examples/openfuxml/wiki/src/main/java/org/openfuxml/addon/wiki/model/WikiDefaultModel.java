package org.openfuxml.addon.wiki.model;

import info.bliki.htmlcleaner.TagNode;
import info.bliki.wiki.filter.MagicWord;
import info.bliki.wiki.model.Configuration;
import info.bliki.wiki.model.WikiModel;

import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiDefaultModel extends WikiModel
{
	final static Logger logger = LoggerFactory.getLogger(WikiDefaultModel.class);
	
	public final static String TL = "{{[[Template:{{{1}}}|{{{1}}}]]}}<noinclude>\n" + "{{pp-template|small=yes}}\n"
			+ "{{documentation}}\n" + "</noinclude>";
	public final static String PRON_ENG = "#REDIRECT [[Template:Pron-en]]";
	public final static String PRON_EN = "<onlyinclude>pronounced <span title=\"Pronunciation in the International Phonetic Alphabet (IPA)\" class=\"IPA\">[[WP:IPA for English|/{{{1}}}/]]</span></onlyinclude>\n"
			+ "\n"
			+ "==Example==\n"
			+ "\n"
			+ "This code:\n"
			+ "\n"
			+ ": <code><nowiki>A battleship, {{pron-en|ˈbætəlʃɪp}}, is ...</nowiki></code>\n"
			+ "\n"
			+ "will display:\n"
			+ "\n"
			+ ": A battleship, {{pron-en|ˈbætəlʃɪp}}, is ...\n"
			+ "\n"
			+ "==Usage==\n"
			+ "{{usage of IPA templates}}\n"
			+ "\n"
			+ "<!-- PLEASE ADD CATEGORIES BELOW THIS LINE, THANKS. -->\n"
			+ "\n"
			+ "[[Category:IPA templates|{{PAGENAME}}]]\n"
			+ "\n"
			+ "<!-- PLEASE ADD INTERWIKIS BELOW THIS LINE, THANKS. -->\n"
			+ "<noinclude>\n"
			+ "[[ar:PronEng]]\n"
			+ "[[tl:Template:PronEng]]\n" + "[[simple:Template:IPA-en]]\n" + "</noinclude>";

	public final static String HTML_START = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
			+ "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \n"
			+ "   \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n"
			+ "<head>\n" + "        <title>test</title>\n\n    </head>\n" + "    <body>";

	public final static String XHTML_START = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
			+ "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \n"
			+ "   \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n";

	public final static String XHTML_END = "</body>\n" + "</html>";

	public final static String PIPE_SYMBOL = "|<noinclude>{{template doc}}</noinclude>";
	public final static String DOUBLE_PARAMETER = "{{{1}}}{{{1}}}";
	public final static String REFLIST_TEXT = "<div class=\"references-small\" {{#if: {{{colwidth|}}}| style=\"-moz-column-width:{{{colwidth}}}; -webkit-column-width:{{{colwidth}}}; column-width:{{{colwidth}}};\" | {{#if: {{{1|}}}| style=\"-moz-column-count:{{{1}}}; -webkit-column-count:{{{1}}}; column-count:{{{1}}} }};\" |}}>\n"
			+ "<references /></div><noinclude>{{pp-template|small=yes}}{{template doc}}</noinclude>\n";

	public final static String CAT = "[[:Category:{{{1}}}]]<noinclude>\n"
			+ "{{Interwikitmp-grp ineligible|CAT|Commons|Wiktionary}}\n" + "Creates a link to the specified category.\n" + "\n"
			+ "\'\'\'Example\'\'\':\n" + "<pre>\n" + "{{Cat|stubs}}\n" + "</pre>\n" + "{{Cat|stubs}}\n" + "\n" + "==See also==\n"
			+ "* {{Tl|Cl}}\n" + "* {{Tl|Ccl}}\n" + "* {{Tl|Lcs}}\n" + "\n" + "[[sl:predloga:kat]]\n" + "</noinclude>\n" + "";

	public final static String CITE_WEB_TEXT = "[{{{url}}} {{{title}}}]";

	public final static String NESTED_TEMPLATE_TEST = "test a {{nested}} template";

	public final static String NESTED = "a nested template text";

	public final static String IFEQ_TEST = "{{#ifeq: {{{1}}}|{{{2}}} | {{{1}}} equals {{{2}}} | {{{1}}} is not equal {{{2}}}}}";

	public final static String ENDLESS_RECURSION_TEST = " {{recursion}} ";
	public final static String TNAVBAR_TEXT = "<includeonly>{{#if:{{{nodiv|}}}|<!--then:\n"
			+ "-->&nbsp;<span class=\"noprint plainlinksneverexpand\" style=\"white-space:nowrap; font-weight:normal; font-size:xx-small; {{{fontstyle|}}}; {{#if:{{{fontcolor|}}}|color:{{{fontcolor}}};}} {{{style|}}}\">|<!--else:\n"
			+ "--><div class=\"noprint plainlinksneverexpand\" style=\"background-color:transparent; padding:0; white-space:nowrap; font-weight:normal; font-size:xx-small; {{{fontstyle|}}}; {{#if:{{{fontcolor|}}}|color:{{{fontcolor}}};}} {{{style|}}}\"><!--\n"
			+ "-->}}<!--\n"
			+ "\n"
			+ "-->{{#ifeq:{{{mini|}}}{{{miniv|}}}{{{plain|}}}{{{viewplain|}}}|<!--equals:-->1|<!--then:\n"
			+ "-->|<!--else:\n"
			+ "-->This box:&nbsp;<!--\n"
			+ "-->}}<!--\n"
			+ "\n"
			+ "-->{{#ifeq:{{{miniv|}}}{{{viewplain|}}}|<!--equals:-->1|<!--then:\n"
			+ "-->[[Template:{{{1}}}|<span title=\"View this template\" style=\"{{{fontstyle|}}};{{#if:{{{fontcolor|}}}|color:{{{fontcolor}}};}}\">{{#if:{{{viewplain|}}}|view|v}}</span>]]|<!--else:\n"
			+ "-->[[Template:{{{1}}}|<span title=\"View this template\" style=\"{{{fontstyle|}}};{{#if:{{{fontcolor|}}}|color:{{{fontcolor}}};}}\">{{#if:{{{mini|}}}|v|view}}</span>]]&nbsp;<span style=\"font-size:80%;\">•</span>&nbsp;[[Template talk:{{{1}}}|<span style=\"color:#002bb8;{{{fontstyle|}}};{{#if:{{{fontcolor|}}}|color:{{{fontcolor}}};}}\" title=\"Discussion about this template\">{{#if:{{{mini|}}}|d|talk}}</span>]]&nbsp;<span style=\"font-size:80%;\">•</span>&nbsp;[{{fullurl:{{ns:10}}:{{{1}}}|action=edit}} <span style=\"color:#002bb8;{{{fontstyle|}}};{{#if:{{{fontcolor|}}}|color:{{{fontcolor}}};}}\" title=\"You can edit this template. Please use the preview button before saving.\">{{#if:{{{mini|}}}|e|edit}}</span>]<!--\n"
			+ "-->}}<!--\n" + "\n" + "-->{{#if:{{{nodiv|}}}|<!--then:\n" + "--></span>&nbsp;|<!--else:\n" + "--></div><!--\n"
			+ "-->}}</includeonly><noinclude>\n" + "\n" + "{{pp-template|small=yes}}\n"
			+ "<hr/><center>\'\'\'{{purge}}\'\'\' the Wikipedia cache of this template.<hr/></center><br/>\n" + "{{documentation}} \n"
			+ "<!--Note: Metadata (interwiki links, etc) for this template should be put on [[Template:Tnavbar/doc]]-->\n"
			+ "</noinclude>";
	public final static String NAVBOX_TEXT = "<!--\n"
			+ "\n"
			+ "Please do not edit without discussion first as this is a VERY complex template.\n"
			+ "\n"
			+ "-->{{#switch:{{{border|{{{1|}}}}}}|subgroup|child=</div>|none=|#default=<table class=\"navbox\" cellspacing=\"0\" <!--\n"
			+ " -->style=\"{{{bodystyle|}}};{{{style|}}}\"><tr><td style=\"padding:2px;\">}}<!--\n"
			+ "\n"
			+ "--><table cellspacing=\"0\" class=\"nowraplinks {{#if:{{{title|}}}|{{#switch:{{{state|}}}|plain|off=|<!--\n"
			+ " -->#default=collapsible {{#if:{{{state|}}}|{{{state|}}}|autocollapse}}}}}} {{#switch:{{{border|{{{1|}}}}}}|<!--\n"
			+ " -->subgroup|child|none=navbox-subgroup\" style=\"width:100%;{{{bodystyle|}}};{{{style|}}}|<!--\n"
			+ " -->#default=\" style=\"width:100%;background:transparent;color:inherit}};{{{innerstyle|}}};\"><!--\n"
			+ "\n"
			+ "\n"
			+ "\n"
			+ "---Title and Navbar---\n"
			+ "-->{{#if:{{{title|}}}|<tr>{{#if:{{{titlegroup|}}}|<!--\n"
			+ " --><td class=\"navbox-group\" style=\"{{{basestyle|}}};{{{groupstyle|}}};{{{titlegroupstyle|}}}\">{{{titlegroup|}}}</td><!--\n"
			+ " --><th style=\"border-left:2px solid #fdfdfd;width:100%;|<th style=\"}}{{{basestyle|}}};{{{titlestyle|}}}\" <!--\n"
			+ " -->colspan={{#expr:2{{#if:{{{imageleft|}}}|+1}}{{#if:{{{image|}}}|+1}}{{#if:{{{titlegroup|}}}|-1}}}} <!--\n"
			+ " -->class=\"navbox-title\"><!--\n"
			+ "\n"
			+ "-->{{#if:{{#switch:{{{navbar|}}}|plain|off=1}}<!--\n"
			+ " -->{{#if:{{{name|}}}||{{#switch:{{{border|{{{1|}}}}}}|subgroup|child|none=1}}}}|<!--\n"
			+ " -->{{#ifeq:{{{navbar|}}}|off|{{#ifeq:{{{state|}}}|plain|<div style=\"float:right;width:6em;\">&nbsp;</div>}}|<!--\n"
			+ " -->{{#ifeq:{{{state|}}}|plain||<div style=\"float:left; width:6em;text-align:left;\">&nbsp;</div>}}}}|<!--\n"
			+ " --><div style=\"float:left; width:6em;text-align:left;\"><!--\n"
			+ " -->{{Tnavbar|{{{name}}}|fontstyle={{{basestyle|}}};{{{titlestyle|}}};border:none;|mini=1}}<!--\n"
			+ " --></div>{{#ifeq:{{{state|}}}|plain|<div style=\"float:right;width:6em;\">&nbsp;</div>}}}}<!--\n"
			+ "\n"
			+ " --><span style=\"font-size:{{#switch:{{{border|{{{1|}}}}}}|subgroup|child|none=100|#default=110}}%;\"><!--\n"
			+ " -->{{{title}}}</span></th></tr>}}<!--\n"
			+ "\n"
			+ "\n"
			+ "\n"
			+ "---Above---\n"
			+ "-->{{#if:{{{above|}}}|<!--\n"
			+ " -->{{#if:{{{title|}}}|<tr style=\"height:2px;\"><td></td></tr>}}<!--\n"
			+ " --><tr><td class=\"navbox-abovebelow\" style=\"{{{basestyle|}}};{{{abovestyle|}}}\" <!--\n"
			+ " -->colspan=\"{{#expr:2{{#if:{{{imageleft|}}}|+1}}{{#if:{{{image|}}}|+1}}}}\">{{{above}}}</td></tr>}}<!--\n"
			+ "\n"
			+ "\n"
			+ "\n"
			+ "---Body---\n"
			+ "\n"
			+ "---First group/list and images---\n"
			+ "-->{{#if:{{{list1|}}}|{{#if:{{{title|}}}{{{above|}}}|<tr style=\"height:2px;\"><td></td></tr>}}<tr><!--\n"
			+ "\n"
			+ "-->{{#if:{{{imageleft|}}}|<!--\n"
			+ " --><td style=\"width:0%;padding:0px 2px 0px 0px;{{{imageleftstyle|}}}\" <!--\n"
			+ " -->rowspan={{#expr:1{{#if:{{{list2|}}}|+2}}{{#if:{{{list3|}}}|+2}}{{#if:{{{list4|}}}|+2}}<!--\n"
			+ " -->{{#if:{{{list5|}}}|+2}}{{#if:{{{list6|}}}|+2}}{{#if:{{{list7|}}}|+2}}{{#if:{{{list8|}}}|+2}}<!--\n"
			+ " -->{{#if:{{{list9|}}}|+2}}{{#if:{{{list10|}}}|+2}}{{#if:{{{list11|}}}|+2}}{{#if:{{{list12|}}}|+2}}<!--\n"
			+ " -->{{#if:{{{list13|}}}|+2}}{{#if:{{{list14|}}}|+2}}{{#if:{{{list15|}}}|+2}}{{#if:{{{list16|}}}|+2}}<!--\n"
			+ " -->{{#if:{{{list17|}}}|+2}}{{#if:{{{list18|}}}|+2}}{{#if:{{{list19|}}}|+2}}{{#if:{{{list20|}}}|+2}}}}><!--\n"
			+ " -->{{{imageleft|}}}</td>}}<!--\n"
			+ "\n"
			+ " -->{{#if:{{{group1|}}}|<td class=\"navbox-group\" style=\"{{{basestyle|}}};{{{groupstyle|}}};{{{group1style|}}}\"><!--\n"
			+ " -->{{{group1}}}</td><td style=\"text-align:left;border-left:2px solid #fdfdfd;|<td colspan=2 style=\"}}<!--\n"
			+ " -->width:100%;padding:0px;{{{liststyle|}}};{{{oddstyle|}}};{{{list1style|}}}\" <!--\n"
			+ " -->class=\"navbox-list navbox-{{#ifeq:{{{evenodd|}}}|swap|even|{{{evenodd|odd}}}}}\"><!--\n"
			+ " --><div style=\"padding:{{{list1padding|{{{listpadding|0em 0.25em}}}}}}\">}}{{{list1|}}}{{#if:{{{list1|}}}|</div></td><!--\n"
			+ "\n"
			+ "-->{{#if:{{{image|}}}|<!--\n"
			+ " --><td style=\"width:0%;padding:0px 0px 0px 2px;{{{imagestyle|}}}\" <!--\n"
			+ " -->rowspan={{#expr:1{{#if:{{{list2|}}}|+2}}{{#if:{{{list3|}}}|+2}}{{#if:{{{list4|}}}|+2}}<!--\n"
			+ " -->{{#if:{{{list5|}}}|+2}}{{#if:{{{list6|}}}|+2}}{{#if:{{{list7|}}}|+2}}{{#if:{{{list8|}}}|+2}}<!--\n"
			+ " -->{{#if:{{{list9|}}}|+2}}{{#if:{{{list10|}}}|+2}}{{#if:{{{list11|}}}|+2}}{{#if:{{{list12|}}}|+2}}<!--\n"
			+ " -->{{#if:{{{list13|}}}|+2}}{{#if:{{{list14|}}}|+2}}{{#if:{{{list15|}}}|+2}}{{#if:{{{list16|}}}|+2}}<!--\n"
			+ " -->{{#if:{{{list17|}}}|+2}}{{#if:{{{list18|}}}|+2}}{{#if:{{{list19|}}}|+2}}{{#if:{{{list20|}}}|+2}}}}><!--\n"
			+ " -->{{{image|}}}</td>}}<!--\n"
			+ "\n"
			+ "--></tr>}}<!--\n"
			+ "\n"
			+ "\n"
			+ "\n"
			+ "---Remaining groups/lists---\n"
			+ "\n"
			+ "-->{{#if:{{{list2|}}}|<!--\n"
			+ " -->{{#if:{{{title|}}}{{{above|}}}{{{list1|}}}|<tr style=\"height:2px\"><td></td></tr>}}<tr><!--\n"
			+ " -->{{#if:{{{group2|}}}|<td class=\"navbox-group\" style=\"{{{basestyle|}}};{{{groupstyle|}}};{{{group2style|}}}\"><!--\n"
			+ " -->{{{group2}}}</td><td style=\"text-align:left;border-left:2px solid #fdfdfd;|<td colspan=2 style=\"}}<!--\n"
			+ " -->width:100%;padding:0px;{{{liststyle|}}};{{{evenstyle|}}};{{{list2style|}}}\" <!--\n"
			+ " -->class=\"navbox-list navbox-{{#ifeq:{{{evenodd|}}}|swap|odd|{{{evenodd|even}}}}}\"><!--\n"
			+ " --><div style=\"padding:{{{listpadding|0em 0.25em}}}\">}}{{{list2|}}}{{#if:{{{list2|}}}|</div></td></tr>}}<!--\n"
			+ "\n"
			+ "-->{{#if:{{{list3|}}}|<!--\n"
			+ " -->{{#if:{{{title|}}}{{{above|}}}{{{list1|}}}{{{list2|}}}|<tr style=\"height:2px\"><td></td></tr>}}<tr><!--\n"
			+ " -->{{#if:{{{group3|}}}|<td class=\"navbox-group\" style=\"{{{basestyle|}}};{{{groupstyle|}}};{{{group3style|}}}\"><!--\n"
			+ " -->{{{group3}}}</td><td style=\"text-align:left;border-left:2px solid #fdfdfd;|<td colspan=2 style=\"}}<!--\n"
			+ " -->width:100%;padding:0px;{{{liststyle|}}};{{{oddstyle|}}};{{{list3style|}}}\" <!--\n"
			+ " -->class=\"navbox-list navbox-{{#ifeq:{{{evenodd|}}}|swap|even|{{{evenodd|odd}}}}}\"><!--\n"
			+ " --><div style=\"padding:{{{listpadding|0em 0.25em}}}\">}}{{{list3|}}}{{#if:{{{list3|}}}|</div></td></tr>}}<!--\n"
			+ "\n"
			+ "-->{{#if:{{{list4|}}}|<!--\n"
			+ " --><tr style=\"height:2px\"><td></td></tr><tr><!--\n"
			+ " -->{{#if:{{{group4|}}}|<td class=\"navbox-group\" style=\"{{{basestyle|}}};{{{groupstyle|}}};{{{group4style|}}}\"><!--\n"
			+ " -->{{{group4}}}</td><td style=\"text-align:left;border-left:2px solid #fdfdfd;|<td colspan=2 style=\"}}<!--\n"
			+ " -->width:100%;padding:0px;{{{liststyle|}}};{{{evenstyle|}}};{{{list4style|}}}\" <!--\n"
			+ " -->class=\"navbox-list navbox-{{#ifeq:{{{evenodd|}}}|swap|odd|{{{evenodd|even}}}}}\"><!--\n"
			+ " --><div style=\"padding:{{{listpadding|0em 0.25em}}}\">}}{{{list4|}}}{{#if:{{{list4|}}}|</div></td></tr>}}<!--\n"
			+ "\n"
			+ "-->{{#if:{{{list5|}}}|<!--\n"
			+ " --><tr style=\"height:2px\"><td></td></tr><tr><!--\n"
			+ " -->{{#if:{{{group5|}}}|<td class=\"navbox-group\" style=\"{{{basestyle|}}};{{{groupstyle|}}};{{{group5style|}}}\"><!--\n"
			+ " -->{{{group5}}}</td><td style=\"text-align:left;border-left:2px solid #fdfdfd;|<td colspan=2 style=\"}}<!--\n"
			+ " -->width:100%;padding:0px;{{{liststyle|}}};{{{oddstyle|}}};{{{list5style|}}}\" <!--\n"
			+ " -->class=\"navbox-list navbox-{{#ifeq:{{{evenodd|}}}|swap|even|{{{evenodd|odd}}}}}\"><!--\n"
			+ " --><div style=\"padding:{{{listpadding|0em 0.25em}}}\">}}{{{list5|}}}{{#if:{{{list5|}}}|</div></td></tr>}}<!--\n"
			+ "\n"
			+ "-->{{#if:{{{list6|}}}|<!--\n"
			+ " --><tr style=\"height:2px\"><td></td></tr><tr><!--\n"
			+ " -->{{#if:{{{group6|}}}|<td class=\"navbox-group\" style=\"{{{basestyle|}}};{{{groupstyle|}}};{{{group6style|}}}\"><!--\n"
			+ " -->{{{group6}}}</td><td style=\"text-align:left;border-left:2px solid #fdfdfd;|<td colspan=2 style=\"}}<!--\n"
			+ " -->width:100%;padding:0px;{{{liststyle|}}};{{{evenstyle|}}};{{{list6style|}}}\" <!--\n"
			+ " -->class=\"navbox-list navbox-{{#ifeq:{{{evenodd|}}}|swap|odd|{{{evenodd|even}}}}}\"><!--\n"
			+ " --><div style=\"padding:{{{listpadding|0em 0.25em}}}\">{{{list6|}}}</div></td></tr>}}<!--\n"
			+ "\n"
			+ "-->{{#if:{{{list7|}}}|<!--\n"
			+ " --><tr style=\"height:2px\"><td></td></tr><tr><!--\n"
			+ " -->{{#if:{{{group7|}}}|<td class=\"navbox-group\" style=\"{{{basestyle|}}};{{{groupstyle|}}};{{{group7style|}}}\"><!--\n"
			+ " -->{{{group7}}}</td><td style=\"text-align:left;border-left:2px solid #fdfdfd;|<td colspan=2 style=\"}}<!--\n"
			+ " -->width:100%;padding:0px;{{{liststyle|}}};{{{oddstyle|}}};{{{list7style|}}}\" <!--\n"
			+ " -->class=\"navbox-list navbox-{{#ifeq:{{{evenodd|}}}|swap|even|{{{evenodd|odd}}}}}\"><!--\n"
			+ " --><div style=\"padding:{{{listpadding|0em 0.25em}}}\">{{{list7|}}}</div></td></tr>}}<!--\n"
			+ "\n"
			+ "-->{{#if:{{{list8|}}}|<!--\n"
			+ " --><tr style=\"height:2px\"><td></td></tr><tr><!--\n"
			+ " -->{{#if:{{{group8|}}}|<td class=\"navbox-group\" style=\"{{{basestyle|}}};{{{groupstyle|}}};{{{group8style|}}}\"><!--\n"
			+ " -->{{{group8}}}</td><td style=\"text-align:left;border-left:2px solid #fdfdfd;|<td colspan=2 style=\"}}<!--\n"
			+ " -->width:100%;padding:0px;{{{liststyle|}}};{{{evenstyle|}}};{{{list8style|}}}\" <!--\n"
			+ " -->class=\"navbox-list navbox-{{#ifeq:{{{evenodd|}}}|swap|odd|{{{evenodd|even}}}}}\"><!--\n"
			+ " --><div style=\"padding:{{{listpadding|0em 0.25em}}}\">{{{list8|}}}</div></td></tr>}}<!--\n"
			+ "\n"
			+ "-->{{#if:{{{list9|}}}|<!--\n"
			+ " --><tr style=\"height:2px\"><td></td></tr><tr><!--\n"
			+ " -->{{#if:{{{group9|}}}|<td class=\"navbox-group\" style=\"{{{basestyle|}}};{{{groupstyle|}}};{{{group9style|}}}\"><!--\n"
			+ " -->{{{group9}}}</td><td style=\"text-align:left;border-left:2px solid #fdfdfd;|<td colspan=2 style=\"}}<!--\n"
			+ " -->width:100%;padding:0px;{{{liststyle|}}};{{{oddstyle|}}};{{{list9style|}}}\" <!--\n"
			+ " -->class=\"navbox-list navbox-{{#ifeq:{{{evenodd|}}}|swap|even|{{{evenodd|odd}}}}}\"><!--\n"
			+ " --><div style=\"padding:{{{listpadding|0em 0.25em}}}\">{{{list9|}}}</div></td></tr>}}<!--\n"
			+ "\n"
			+ "-->{{#if:{{{list10|}}}|<!--\n"
			+ " --><tr style=\"height:2px\"><td></td></tr><tr><!--\n"
			+ " -->{{#if:{{{group10|}}}|<td class=\"navbox-group\" style=\"{{{basestyle|}}};{{{groupstyle|}}};{{{group10style|}}}\"><!--\n"
			+ " -->{{{group10}}}</td><td style=\"text-align:left;border-left:2px solid #fdfdfd;|<td colspan=2 style=\"}}<!--\n"
			+ " -->width:100%;padding:0px;{{{liststyle|}}};{{{evenstyle|}}};{{{list10style|}}}\" <!--\n"
			+ " -->class=\"navbox-list navbox-{{#ifeq:{{{evenodd|}}}|swap|odd|{{{evenodd|even}}}}}\"><!--\n"
			+ " --><div style=\"padding:{{{listpadding|0em 0.25em}}}\">{{{list10|}}}</div></td></tr>}}<!--\n"
			+ "\n"
			+ "-->{{#if:{{{list11|}}}|<!--\n"
			+ " --><tr style=\"height:2px\"><td></td></tr><tr><!--\n"
			+ " -->{{#if:{{{group11|}}}|<td class=\"navbox-group\" style=\"{{{basestyle|}}};{{{groupstyle|}}};{{{group11style|}}}\"><!--\n"
			+ " -->{{{group11}}}</td><td style=\"text-align:left;border-left:2px solid #fdfdfd;|<td colspan=2 style=\"}}<!--\n"
			+ " -->width:100%;padding:0px;{{{liststyle|}}};{{{oddstyle|}}};{{{list11style|}}}\" <!--\n"
			+ " -->class=\"navbox-list navbox-{{#ifeq:{{{evenodd|}}}|swap|even|{{{evenodd|odd}}}}}\"><!--\n"
			+ " --><div style=\"padding:{{{listpadding|0em 0.25em}}}\">{{{list11|}}}</div></td></tr>}}<!--\n"
			+ "\n"
			+ "-->{{#if:{{{list12|}}}|<!--\n"
			+ " --><tr style=\"height:2px\"><td></td></tr><tr><!--\n"
			+ " -->{{#if:{{{group12|}}}|<td class=\"navbox-group\" style=\"{{{basestyle|}}};{{{groupstyle|}}};{{{group12style|}}}\"><!--\n"
			+ " -->{{{group12}}}</td><td style=\"text-align:left;border-left:2px solid #fdfdfd;|<td colspan=2 style=\"}}<!--\n"
			+ " -->width:100%;padding:0px;{{{liststyle|}}};{{{evenstyle|}}};{{{list12style|}}}\" <!--\n"
			+ " -->class=\"navbox-list navbox-{{#ifeq:{{{evenodd|}}}|swap|odd|{{{evenodd|even}}}}}\"><!--\n"
			+ " --><div style=\"padding:{{{listpadding|0em 0.25em}}}\">{{{list12|}}}</div></td></tr>}}<!--\n"
			+ "\n"
			+ "-->{{#if:{{{list13|}}}|<!--\n"
			+ " --><tr style=\"height:2px\"><td></td></tr><tr><!--\n"
			+ " -->{{#if:{{{group13|}}}|<td class=\"navbox-group\" style=\"{{{basestyle|}}};{{{groupstyle|}}};{{{group13style|}}}\"><!--\n"
			+ " -->{{{group13}}}</td><td style=\"text-align:left;border-left:2px solid #fdfdfd;|<td colspan=2 style=\"}}<!--\n"
			+ " -->width:100%;padding:0px;{{{liststyle|}}};{{{oddstyle|}}};{{{list13style|}}}\" <!--\n"
			+ " -->class=\"navbox-list navbox-{{#ifeq:{{{evenodd|}}}|swap|even|{{{evenodd|odd}}}}}\"><!--\n"
			+ " --><div style=\"padding:{{{listpadding|0em 0.25em}}}\">{{{list13|}}}</div></td></tr>}}<!--\n"
			+ "\n"
			+ "-->{{#if:{{{list14|}}}|<!--\n"
			+ " --><tr style=\"height:2px\"><td></td></tr><tr><!--\n"
			+ " -->{{#if:{{{group14|}}}|<td class=\"navbox-group\" style=\"{{{basestyle|}}};{{{groupstyle|}}};{{{group14style|}}}\"><!--\n"
			+ " -->{{{group14}}}</td><td style=\"text-align:left;border-left:2px solid #fdfdfd;|<td colspan=2 style=\"}}<!--\n"
			+ " -->width:100%;padding:0px;{{{liststyle|}}};{{{evenstyle|}}};{{{list14style|}}}\" <!--\n"
			+ " -->class=\"navbox-list navbox-{{#ifeq:{{{evenodd|}}}|swap|odd|{{{evenodd|even}}}}}\"><!--\n"
			+ " --><div style=\"padding:{{{listpadding|0em 0.25em}}}\">{{{list14|}}}</div></td></tr>}}<!--\n"
			+ "\n"
			+ "-->{{#if:{{{list15|}}}|<!--\n"
			+ " --><tr style=\"height:2px\"><td></td></tr><tr><!--\n"
			+ " -->{{#if:{{{group15|}}}|<td class=\"navbox-group\" style=\"{{{basestyle|}}};{{{groupstyle|}}};{{{group15style|}}}\"><!--\n"
			+ " -->{{{group15}}}</td><td style=\"text-align:left;border-left:2px solid #fdfdfd;|<td colspan=2 style=\"}}<!--\n"
			+ " -->width:100%;padding:0px;{{{liststyle|}}};{{{oddstyle|}}};{{{list15style|}}}\" <!--\n"
			+ " -->class=\"navbox-list navbox-{{#ifeq:{{{evenodd|}}}|swap|even|{{{evenodd|odd}}}}}\"><!--\n"
			+ " --><div style=\"padding:{{{listpadding|0em 0.25em}}}\">{{{list15|}}}</div></td></tr>}}<!--\n"
			+ "\n"
			+ "-->{{#if:{{{list16|}}}|<!--\n"
			+ " --><tr style=\"height:2px\"><td></td></tr><tr><!--\n"
			+ " -->{{#if:{{{group16|}}}|<td class=\"navbox-group\" style=\"{{{basestyle|}}};{{{groupstyle|}}};{{{group16style|}}}\"><!--\n"
			+ " -->{{{group16}}}</td><td style=\"text-align:left;border-left:2px solid #fdfdfd;|<td colspan=2 style=\"}}<!--\n"
			+ " -->width:100%;padding:0px;{{{liststyle|}}};{{{evenstyle|}}};{{{list16style|}}}\" <!--\n"
			+ " -->class=\"navbox-list navbox-{{#ifeq:{{{evenodd|}}}|swap|odd|{{{evenodd|even}}}}}\"><!--\n"
			+ " --><div style=\"padding:{{{listpadding|0em 0.25em}}}\">{{{list16|}}}</div></td></tr>}}<!--\n"
			+ "\n"
			+ "-->{{#if:{{{list17|}}}|<!--\n"
			+ " --><tr style=\"height:2px\"><td></td></tr><tr><!--\n"
			+ " -->{{#if:{{{group17|}}}|<td class=\"navbox-group\" style=\"{{{basestyle|}}};{{{groupstyle|}}};{{{group17style|}}}\"><!--\n"
			+ " -->{{{group17}}}</td><td style=\"text-align:left;border-left:2px solid #fdfdfd;|<td colspan=2 style=\"}}<!--\n"
			+ " -->width:100%;padding:0px;{{{liststyle|}}};{{{oddstyle|}}};{{{list17style|}}}\" <!--\n"
			+ " -->class=\"navbox-list navbox-{{#ifeq:{{{evenodd|}}}|swap|even|{{{evenodd|odd}}}}}\"><!--\n"
			+ " --><div style=\"padding:{{{listpadding|0em 0.25em}}}\">{{{list17|}}}</div></td></tr>}}<!--\n"
			+ "\n"
			+ "-->{{#if:{{{list18|}}}|<!--\n"
			+ " --><tr style=\"height:2px\"><td></td></tr><tr><!--\n"
			+ " -->{{#if:{{{group18|}}}|<td class=\"navbox-group\" style=\"{{{basestyle|}}};{{{groupstyle|}}};{{{group18style|}}}\"><!--\n"
			+ " -->{{{group18}}}</td><td style=\"text-align:left;border-left:2px solid #fdfdfd;|<td colspan=2 style=\"}}<!--\n"
			+ " -->width:100%;padding:0px;{{{liststyle|}}};{{{evenstyle|}}};{{{list18style|}}}\" <!--\n"
			+ " -->class=\"navbox-list navbox-{{#ifeq:{{{evenodd|}}}|swap|odd|{{{evenodd|even}}}}}\"><!--\n"
			+ " --><div style=\"padding:{{{listpadding|0em 0.25em}}}\">{{{list18|}}}</div></td></tr>}}<!--\n"
			+ "\n"
			+ "-->{{#if:{{{list19|}}}|<!--\n"
			+ " --><tr style=\"height:2px\"><td></td></tr><tr><!--\n"
			+ " -->{{#if:{{{group19|}}}|<td class=\"navbox-group\" style=\"{{{basestyle|}}};{{{groupstyle|}}};{{{group19style|}}}\"><!--\n"
			+ " -->{{{group19}}}</td><td style=\"text-align:left;border-left:2px solid #fdfdfd;|<td colspan=2 style=\"}}<!--\n"
			+ " -->width:100%;padding:0px;{{{liststyle|}}};{{{oddstyle|}}};{{{list19style|}}}\" <!--\n"
			+ " -->class=\"navbox-list navbox-{{#ifeq:{{{evenodd|}}}|swap|even|{{{evenodd|odd}}}}}\"><!--\n"
			+ " --><div style=\"padding:{{{listpadding|0em 0.25em}}}\">{{{list19|}}}</div></td></tr>}}<!--\n"
			+ "\n"
			+ "-->{{#if:{{{list20|}}}|<!--\n"
			+ " --><tr style=\"height:2px\"><td></td></tr><tr><!--\n"
			+ " -->{{#if:{{{group20|}}}|<td class=\"navbox-group\" style=\"{{{basestyle|}}};{{{groupstyle|}}};{{{group20style|}}}\"><!--\n"
			+ " -->{{{group20}}}</td><td style=\"text-align:left;border-left:2px solid #fdfdfd;|<td colspan=2 style=\"}}<!--\n"
			+ " -->width:100%;padding:0px;{{{liststyle|}}};{{{evenstyle|}}};{{{list20style|}}}\" <!--\n"
			+ " -->class=\"navbox-list navbox-{{#ifeq:{{{evenodd|}}}|swap|odd|{{{evenodd|even}}}}}\"><!--\n"
			+ " --><div style=\"padding:{{{listpadding|0em 0.25em}}}\">{{{list20|}}}</div></td></tr>}}<!--\n" + "\n" + "\n"
			+ "---Below---\n" + "-->{{#if:{{{below|}}}|<!--\n"
			+ " -->{{#if:{{{title|}}}{{{above|}}}{{{list1|}}}{{{list2|}}}{{{list3|}}}|<tr style=\"height:2px;\"><td></td></tr>}}<!--\n"
			+ " --><tr><td class=\"navbox-abovebelow\" style=\"{{{basestyle|}}};{{{belowstyle|}}}\" <!--\n"
			+ " -->colspan=\"{{#expr:2{{#if:{{{imageleft|}}}|+1}}{{#if:{{{image|}}}|+1}}}}\">{{{below}}}</td></tr>}}<!--\n" + "\n" + "\n"
			+ "--></table>{{#switch:{{{border|{{{1|}}}}}}|subgroup|child=<div>|none=|#default=</td></tr></table>}}<!--\n" + "\n"
			+ "--><noinclude>\n" + "\n" + "{{pp-template|small=yes}}\n" + "\n" + "{{documentation}}\n"
			+ "<!-- Add categories and interwikis to the /doc subpage, not here! -->\n" + "</noinclude>";

	public final static String INFOBOX_SOFTWARE_TEXT = "<includeonly>{| class=\"infobox\" cellspacing=\"5\" style=\"width: 21em; font-size: 90%; text-align: left;\"\n"
			+ "! colspan=\"2\" style=\"text-align: center; font-size: 130%;\" | {{{title|{{{name|{{PAGENAME}}}}}}}}\n"
			+ "|-\n"
			+ "{{#if:{{{logo|}}}|\n"
			+ "{{!}} colspan=\"2\" style=\"text-align: center;\" {{!}} {{{logo|}}}\n"
			+ "{{!}}-\n"
			+ "}}\n"
			+ "{{#if:{{{collapsible|}}}|\n"
			+ "{{!}} colspan=\"2\" {{!}}\n"
			+ "{{hidden|Screenshot|{{{screenshot}}}{{#if:{{{caption|}}}|<br />{{{caption|}}}}}|bg1=#ccccff|ta2=center}}\n"
			+ "{{!}}-\n"
			+ "|\n"
			+ "{{#if:{{{screenshot|}}}|\n"
			+ "{{!}} colspan=\"2\" style=\"text-align: center;\" {{!}} {{{screenshot|}}}{{#if:{{{caption|}}}|<br />{{{caption|}}}}}\n"
			+ "{{!}}-\n"
			+ "}}\n"
			+ "}}\n"
			+ "{{#if:{{{author|}}}|\n"
			+ "! [[Software design|Design by]]\n"
			+ "{{!}} {{{author|}}}\n"
			+ "{{!}}-\n"
			+ "}}\n"
			+ "{{#if:{{{developer|}}}|\n"
			+ "! [[Software developer|Developed by]]\n"
			+ "{{!}} {{{developer|}}}\n"
			+ "{{!}}-\n"
			+ "}}\n"
			+ "{{#if:{{{released|}}}|\n"
			+ "! Initial release\n"
			+ "{{!}} {{{released|}}}\n"
			+ "{{!}}-\n"
			+ "}}\n"
			+ "{{#if:{{{frequently updated|{{{frequently_updated|}}}}}}|<!-- then -->\n"
			+ "{{#ifexist:Template:Latest stable release/{{{name|{{PAGENAME}}}}}|\n"
			+ "! [[Software release|Stable release]]\n"
			+ "{{!}} {{Latest stable release/{{{name|{{PAGENAME}}}}}}} <sub class=\"plainlinks\">[[{{SERVER}}{{localurl:Template:Latest_stable_release/{{{name|{{PAGENAME}}}}}|action=edit&preload=Template:LSR/syntax}} +/−]]</sub>\n"
			+ "{{!}}-\n"
			+ "}}\n"
			+ "{{#ifexist:Template:Latest preview release/{{{name|{{PAGENAME}}}}}|\n"
			+ "! [[Software release|Preview release]]\n"
			+ "{{!}} {{Latest preview release/{{{name|{{PAGENAME}}}}}}} <sub class=\"plainlinks\">[[{{SERVER}}{{localurl:Template:Latest_preview_release/{{{name|{{PAGENAME}}}}}|action=edit&preload=Template:LPR/syntax}} +/−]]</sub>\n"
			+ "{{!}}-\n"
			+ "}}\n"
			+ "|<!-- else -->\n"
			+ "{{#if:{{{latest release version|{{{latest_release_version|}}}}}}|\n"
			+ "! [[Software release life cycle|Latest release]]\n"
			+ "{{!}} {{{latest release version|{{{latest_release_version|}}}}}} {{#if:{{{latest release date|{{{latest_release_date|}}}}}}| / {{{latest release date|{{{latest_release_date|}}}}}}}}\n"
			+ "{{!}}-\n"
			+ "}}\n"
			+ "{{#if:{{{latest preview version|{{{latest_preview_version|}}}}}}|\n"
			+ "! [[Software release life cycle|Preview release]]\n"
			+ "{{!}} {{{latest preview version|{{{latest_preview_version|}}}}}} {{#if:{{{latest preview date|{{{latest_preview_date|}}}}}}| / {{{latest preview date|{{{latest_preview_date|}}}}}}}}\n"
			+ "{{!}}-\n"
			+ "}}\n"
			+ "}}\n"
			+ "{{#if:{{{programming language|{{{programming_language|}}}}}}|\n"
			+ "! [[Programming language|Written in]]\n"
			+ "{{!}} {{{programming language|{{{programming_language|}}}}}}\n"
			+ "{{!}}-\n"
			+ "}}\n"
			+ "{{#if:{{{operating system|{{{operating_system|}}}}}}|\n"
			+ "! [[Operating system|OS]]\n"
			+ "{{!}} {{{operating system|{{{operating_system}}}}}}\n"
			+ "{{!}}-\n"
			+ "}}\n"
			+ "{{#if:{{{platform|}}}|\n"
			+ "! [[Platform (computing)|Platform]]\n"
			+ "{{!}} {{{platform|}}}\n"
			+ "{{!}}-\n"
			+ "}}\n"
			+ "{{#if:{{{size|}}}|\n"
			+ "! [[File size|Size]]\n"
			+ "{{!}} {{{size|}}}\n"
			+ "{{!}}-\n"
			+ "}}\n"
			+ "{{#if:{{{language|}}}|\n"
			+ "! [[Language|Available in]]\n"
			+ "{{!}} {{{language|}}}\n"
			+ "{{!}}-\n"
			+ "}}\n"
			+ "{{#if:{{{status|}}}|\n"
			+ "! Development status\n"
			+ "{{!}} {{{status|}}}\n"
			+ "{{!}}-\n"
			+ "}}\n"
			+ "{{#if:{{{genre|}}}|\n"
			+ "! [[List of software categories|Genre]]\n"
			+ "{{!}} {{{genre|}}}\n"
			+ "{{!}}-\n"
			+ "}}\n"
			+ "{{#if:{{{license|}}}|\n"
			+ "! [[Software license|License]]\n"
			+ "{{!}} {{{license|}}}\n"
			+ "{{!}}-\n"
			+ "}}\n"
			+ "{{#if:{{{licence|}}}|\n"
			+ "! [[Software license|Licence]]\n"
			+ "{{!}} {{{licence|}}}\n"
			+ "{{!}}-\n"
			+ "}}\n"
			+ "{{#if:{{{website|}}}|\n"
			+ "! [[Website]]\n"
			+ "{{!}} {{{website|}}}\n"
			+ "{{!}}-\n"
			+ "}}\n"
			+ "{{#if:{{{standard|}}}|\n"
			+ "! [[Standard]](s)\n"
			+ "{{!}} {{{standard|}}}\n"
			+ "{{!}}-\n"
			+ "}}\n"
			+ "{{#if:{{{AsOf|}}}|\n"
			+ "! As of\n"
			+ "{{!}} {{{AsOf|}}}\n"
			+ "{{!}}-\n"
			+ "}}\n"
			+ "|}</includeonly><noinclude>\n"
			+ "{{pp-template|small=yes}}\n"
			+ "{{documentation}}\n"
			+ "<!-- Add cats and interwikis to the /doc subpage, not here! -->\n" + "</noinclude>";

	final static String FURTHER = "<includeonly>:<span class=\"boilerplate further\"\n"
			+ ">''{{{altphrase|Further information}}}: {{#if:{{{1|}}} |<!--then:-->{{{1}}} |<!--\n"
			+ "else:-->'''Error: [[Template:Further|Template must be given at least one article name]]''' \n"
			+ "}}{{#if:{{{2|}}}|{{#if:{{{3|}}}|, |&#32;and }}  {{{2}}}\n" + "}}{{#if:{{{3|}}}|{{#if:{{{4|}}}|, |, and }} {{{3}}}\n"
			+ "}}{{#if:{{{4|}}}|{{#if:{{{5|}}}|, |, and }} {{{4}}}\n" + "}}{{#if:{{{5|}}}|, and {{{5}}}\n"
			+ "}}{{#if:{{{6|}}}| — '''<br/>Error: [[Template:Futher|Too many links specified (maximum is 5)]]'''\n"
			+ "}}''</span></includeonly><!-- includeonly block is needed, as otherwise the bare template gives error message \n"
			+ "\"Error: Template must be given at least one article name\"\n" + " ---><noinclude>\n" + "{{template doc}}</noinclude>";

	boolean fSemanticWebActive;

	static {
		TagNode.addAllowedAttribute("style");
	}

	public WikiDefaultModel(String imageBaseURL, String linkBaseURL)
	{
		this(Locale.ENGLISH, imageBaseURL, linkBaseURL);
	}

	/**
	 * Add German namespaces to the wiki model
	 * 
	 * @param imageBaseURL
	 * @param linkBaseURL
	 */
	public WikiDefaultModel(Locale locale, String imageBaseURL, String linkBaseURL) {
		super(Configuration.DEFAULT_CONFIGURATION, locale, imageBaseURL, linkBaseURL);
		fSemanticWebActive = false;
	}

	/**
	 * Add templates: &quot;Test&quot;, &quot;Templ1&quot;, &quot;Templ2&quot;,
	 * &quot;Include Page&quot;
	 * 
	 */
	@Override
	public String getRawWikiContent(String namespace, String articleName, Map<String, String> map) {
		String result = super.getRawWikiContent(namespace, articleName, map);
		if (result != null) {
			return result;
		}
		String name = encodeTitleToUrl(articleName, true);
		if (isTemplateNamespace(namespace))
		{
			if (MagicWord.isMagicWord(articleName))
			{
				logger.warn("API of BLIKI has changed, this needs to be cheked!");
				//TODO OFX-14
				//return MagicWord.processMagicWord(articleName, this);
			}
			if (name.equals("Reflist")) {
				return REFLIST_TEXT;
			} else if (name.equals("!")) {
				return PIPE_SYMBOL;
			} else if (name.equals("2x")) {
				return DOUBLE_PARAMETER;
			} else if (name.equals("Cat")) {
				return CAT;
			} else if (name.equals("!")) {
				return "|<noinclude>{{template doc}}</noinclude>";
			} else if (name.equals("Infobox_Software")) {
				return INFOBOX_SOFTWARE_TEXT;
			} else if (name.equals("Cite_web")) {
				return CITE_WEB_TEXT;
			} else if (name.equals("Navbox")) {
				return NAVBOX_TEXT;
			} else if (name.equals("Tnavbar")) {
				return TNAVBAR_TEXT;
			} else if (name.equals("Nested_tempplate_test")) {
				return NESTED_TEMPLATE_TEST;
			} else if (name.equals("Nested")) {
				return NESTED;
			} else if (name.equals("Recursion")) {
				return ENDLESS_RECURSION_TEST;
			} else if (name.equals("Test")) {
				return "a) First: {{{1}}} Second: {{{2}}}";
			} else if (name.equals("Templ1")) {
				return "b) First: {{{a}}} Second: {{{2}}}";
			} else if (name.equals("Templ2")) {
				return "c) First: {{{1}}} Second: {{{2}}}";
			} else if (name.equals("Ifeq")) {
				return IFEQ_TEST;
			} else if (name.equals("Further")) {
				return FURTHER;
			} else if (name.equals("Tl")) {
				return TL;
			} else if (name.equals("PronEng")) {
				return PRON_ENG;
			} else if (name.equals("Pron-en")) {
				return PRON_EN;
			}
		} else {
			if (name.equals("Include_Page")) {
				return "an include page";
			}
		}
		return null;
	}

	/**
	 * Set the German image namespace
	 */
	@Override
	public String getImageNamespace()
	{
		return "Bild";
	}

	@Override
	public boolean isImageNamespace(String name)
	{
		return super.isImageNamespace(name) || name.equals(getImageNamespace());
	}

	@Override
	public boolean isSemanticWebActive() {
		return fSemanticWebActive;
	}

	@Override
	public void setSemanticWebActive(boolean semanticWeb) {
		this.fSemanticWebActive = semanticWeb;
	}

	public boolean showSyntax(String tagName) {
		// if (tagName.equals("groovy")) {
		// return false;
		// }
		return true;
	}

	@Override
	public String render(String rawWikiText) {
		String xhtmlArtifact = super.render(rawWikiText);
		// byte[] bytes;
		// try {
		// String xhtml = HTML_START + xhtmlArtifact + XHTML_END;
		//
		// bytes = xhtml.getBytes("UTF-8");
		//
		// InputStream in = new ByteArrayInputStream(bytes);
		// Parser parser = new Parser();
		// HTMLSchema schema = new HTMLSchema();
		// parser.setProperty(Parser.schemaProperty, schema);
		// Writer w = new StringWriter();
		// XMLWriter x = new XMLWriter(w);
		// x.setOutputProperty(XMLWriter.METHOD, "xml");
		// x.setOutputProperty(XMLWriter.OMIT_XML_DECLARATION, "yes");
		// // x.setPrefix(schema.getURI(), "");
		//
		// parser.setFeature(Parser.namespacesFeature, false);
		// parser.setFeature(Parser.defaultAttributesFeature, true);
		// parser.setContentHandler(x);
		// InputSource is = new InputSource(in);
		// is.setEncoding("UTF-8");
		// parser.parse(is);
		// XhtmlValidator validator = new XhtmlValidator();
		// xhtml = w.toString();
		// bytes = (XHTML_START + xhtml).getBytes("UTF-8");
		// in = new ByteArrayInputStream(bytes);
		// validator.isValid(in);
		// String[] errors = validator.getErrors();
		// if (errors.length > 0) {
		// System.out.println(">>>>>");
		// for (int i = 0; i < errors.length; i++) {
		// System.out.println(errors[i]);
		// }
		// // junit.framework.Assert.assertEquals("", errors[0]);
		// System.out.println(xhtml);
		// System.out.println("<<<<<");
		// }
		// return xhtmlArtifact;
		// } catch (IOException e) {
		// e.printStackTrace();
		// } catch (SAXException e) {
		// e.printStackTrace();
		// // } catch (UnsupportedEncodingException e) {
		// // e.printStackTrace();
		// }
		return xhtmlArtifact;
	}

	/**
	 * Test for <a
	 * href="http://groups.google.de/group/bliki/t/a0540e27f27f02a5">Discussion:
	 * Hide Table of Contents (toc)?</a>
	 */
	// public ITableOfContent createTableOfContent(boolean isTOCIdentifier) {
	// if (fToCSet == null) {
	// fToCSet = new HashSet<String>();
	// fTableOfContent = new ArrayList<Object>();
	// }
	// fTableOfContentTag = new TableOfContentTag("div") {
	// public void setShowToC(boolean showToC) {
	// // do nothing
	// }
	// };
	// return fTableOfContentTag;
	// }
}
