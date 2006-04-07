package org.apache.maven.doxia.module.xhtml;

/*
 * Copyright 2004-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.doxia.module.HtmlTools;
import org.apache.maven.doxia.module.xhtml.decoration.render.RenderingContext;
import org.apache.maven.doxia.parser.Parser;
import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.doxia.sink.StructureSink;
import org.apache.maven.doxia.util.StringUtil;
import org.codehaus.plexus.util.StringUtils;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

/**
 * A doxia sink which produces xhtml
 *
 * @author <a href="mailto:jason@maven.org">Jason van Zyl</a>
 */
public class XhtmlSink
    extends AbstractXhtmlSink
{
    private StringBuffer buffer = new StringBuffer();

    private boolean headFlag;

    private int itemFlag;

    private boolean verbatimFlag;

    private int cellCount;

    private PrintWriter writer;

    private StringsMap directives;

    private RenderingContext renderingContext;

    private int[] cellJustif;

    public XhtmlSink( Writer writer, RenderingContext renderingContext, Map directives )
    {
        this.writer = new PrintWriter( writer );

        this.directives = new StringsMap( directives );

        this.renderingContext = renderingContext;
    }

    protected StringBuffer getBuffer()
    {
        return buffer;
    }

    protected void setHeadFlag( boolean headFlag )
    {
        this.headFlag = headFlag;
    }

    protected void resetState()
    {
        headFlag = false;

        resetBuffer();

        itemFlag = 0;

        verbatimFlag = false;

        cellCount = 0;
    }

    protected void resetBuffer()
    {
        buffer = new StringBuffer();
    }

    public void head()
    {
        directive( "head()" );

        resetState();

        headFlag = true;
    }

    public void head_()
    {
        headFlag = false;

        directive( "head_()" );
    }

    public void title()
    {
        write( "<title>" );
    }

    public void title_()
    {
        write( buffer.toString() );

        write( "</title>" );

        resetBuffer();
    }

    public void author_()
    {
        if ( buffer.length() > 0 )
        {
            write( "<meta name=\"author\" content=\"" );
            write( buffer.toString() );
            write( "\" />\n" );
            resetBuffer();
        }
    }

    public void date_()
    {
        if ( buffer.length() > 0 )
        {
            write( "<meta name=\"date\" content=\"" );
            write( buffer.toString() );
            write( "\" />\n" );
            resetBuffer();
        }
    }

    public void body()
    {
        String body = directiveValue( "body()" );

        write( body );
    }

    public void body_()
    {
        String body = directiveValue( "body_()" );

        write( body );

        resetState();
    }

    // ----------------------------------------------------------------------
    // Sections
    // ----------------------------------------------------------------------

    public void section1()
    {
        write( "<div class=\"section\">" );
    }

    public void section2()
    {
        write( "<div class=\"section\">" );
    }

    public void section3()
    {
        write( "<div class=\"section\">" );
    }

    public void section4()
    {
        write( "<div class=\"section\">" );
    }

    public void section5()
    {
        write( "<div class=\"section\">" );
    }

    public void section1_()
    {
        write( "</div>" );
    }

    public void section2_()
    {
        write( "</div>" );
    }

    public void section3_()
    {
        write( "</div>" );
    }

    public void section4_()
    {
        write( "</div>" );
    }

    public void section5_()
    {
        write( "</div>" );
    }

    public void sectionTitle1()
    {
        write( "<h2>" );
    }

    public void sectionTitle1_()
    {
        write( "</h2>" );
    }

    public void sectionTitle2()
    {
        write( "<h3>" );
    }

    public void sectionTitle2_()
    {
        write( "</h3>" );
    }

    public void sectionTitle3()
    {
        write( "<h4>" );
    }

    public void sectionTitle3_()
    {
        write( "</h4>" );
    }

    public void sectionTitle4()
    {
        write( "<h5>" );
    }

    public void sectionTitle4_()
    {
        write( "</h5>" );
    }

    public void sectionTitle5()
    {
        write( "<h6>" );
    }

    public void sectionTitle5_()
    {
        write( "</h6>" );
    }

    // ----------------------------------------------------------------------
    //
    // ----------------------------------------------------------------------

    public void list()
    {
        write( "<ul>" );
    }

    public void list_()
    {
        write( "</ul>" );
    }

    public void listItem()
    {
        write( "<li>" );

        itemFlag++;
        // What follows is at least a paragraph.
    }

    public void listItem_()
    {
        write( "</li>" );
    }

    public void numberedList( int numbering )
    {
        write("<ol type=\"");
        switch ( numbering )
        {
            case Sink.NUMBERING_LOWER_ALPHA:
                write("a");
                break;
            case Sink.NUMBERING_UPPER_ALPHA:
                write("A");
                break;
            case Sink.NUMBERING_LOWER_ROMAN:
                write("i");
                break;
            case Sink.NUMBERING_UPPER_ROMAN:
                write("I");
                break;
            case Sink.NUMBERING_DECIMAL:
            default:
                write("1");
        }
        write("\">");
    }

    public void numberedList_()
    {
        write( "</ol>" );
    }

    public void numberedListItem()
    {
        write( "<li>" );

        itemFlag++;
        // What follows is at least a paragraph.
    }

    public void numberedListItem_()
    {
        write( "</li>" );
    }

    public void definitionList()
    {
        write( "<dl>" );
    }

    public void definitionList_()
    {
        write( "</dl>" );
    }

    public void definedTerm()
    {
        write( "<dt>" );
    }

    public void definedTerm_()
    {
        write( "</dt>" );
    }

    public void definition()
    {
        write( "<dd>" );

        itemFlag++;
        // What follows is at least a paragraph.
    }

    public void definition_()
    {
        write( "</dd>" );
    }

    public void paragraph()
    {
        if ( itemFlag == 0 )
        {
            write( "<p>" );
        }
    }

    public void paragraph_()
    {
        if ( itemFlag == 0 )
        {
            write( "</p>" );
        }
        else
        {
            itemFlag--;
        }
    }

    public void verbatim( boolean boxed )
    {
        verbatimFlag = true;

        write( "<div class=\"source\"><pre>" );
    }

    public void verbatim_()
    {
        write( "</pre></div>" );

        verbatimFlag = false;

    }

    public void horizontalRule()
    {
        write( "<hr />" );
    }

    public void table()
    {
        write( "<table class=\"bodyTable\">" );
    }

    public void table_()
    {
        write( "</table>" );
    }

    public void tableRows( int[] justification, boolean grid )
    {
        write( "<tbody>" );

        cellJustif = justification;
    }

    public void tableRows_()
    {
        write( "</tbody>" );

        cellJustif = null;
    }

    private int rowMarker = 0;

    //TODO: could probably make this more flexible but really i would just like a standard xhtml structure.
    public void tableRow()
    {
        if ( rowMarker == 0 )
        {
            write( "<tr class=\"a\">" );

            rowMarker = 1;
        }
        else
        {
            write( "<tr class=\"b\">" );

            rowMarker = 0;
        }

        cellCount = 0;
    }

    public void tableRow_()
    {
        write( "</tr>" );

        cellCount = 0;
    }

    public void tableCell()
    {
        tableCell( false );
    }

    public void tableHeaderCell()
    {
        tableCell( true );
    }

    public void tableCell( boolean headerRow )
    {
        String justif = null;

        if ( cellJustif != null )
        {
            switch ( cellJustif[cellCount] )
            {
                case Parser.JUSTIFY_LEFT:
                    justif = "left";
                    break;
                case Parser.JUSTIFY_RIGHT:
                    justif = "right";
                    break;
                case Parser.JUSTIFY_CENTER:
                    justif = "center";
                    break;
            }
        }

        if ( headerRow )
        {
            write( "<th" );
        }
        else
        {
            write( "<td" );
        }

        if ( justif != null )
        {
            write( " align=\"" + justif + "\"" );
        }

        write( ">" );
    }

    public void tableCell( String width )
    {
        tableCell( false, width );
    }

    public void tableHeaderCell( String width )
    {
        tableCell( true, width );
    }

    public void tableCell( boolean headerRow, String width )
    {
        String justif = null;

        if ( cellJustif != null )
        {
            switch ( cellJustif[cellCount] )
            {
                case Parser.JUSTIFY_LEFT:
                    justif = "left";
                    break;
                case Parser.JUSTIFY_RIGHT:
                    justif = "right";
                    break;
                case Parser.JUSTIFY_CENTER:
                    justif = "center";
                    break;
            }
        }

        String cellWidth = "width=\"" + width + "\"";

        if ( headerRow )
        {
            write( "<th " );
        }
        else
        {
            write( "<td " );
        }

        if ( justif != null )
        {
            write( " align=\"" + justif + "\"" );
        }

        write( cellWidth + " >" );
    }

    public void tableCell_()
    {
        tableCell_( false );
    }

    public void tableHeaderCell_()
    {
        tableCell_( true );
    }

    public void tableCell_( boolean headerRow )
    {
        if ( headerRow )
        {
            write( "</th>" );
        }
        else
        {
            write( "</td>" );
        }

        ++cellCount;
    }

    public void tableCaption()
    {
        write( "<caption>" );
    }

    public void tableCaption_()
    {
        write( "</caption>" );
    }

    /**
     * @see org.apache.maven.doxia.sink.SinkAdapter#figure()
     */
    public void figure()
    {
        write( "<img" );
    }

    /**
     * @see org.apache.maven.doxia.sink.SinkAdapter#figure_()
     */
    public void figure_()
    {
        write( " />" );
    }

    /**
     * @see org.apache.maven.doxia.sink.SinkAdapter#figureCaption()
     */
    public void figureCaption()
    {
        write( " alt=\"" );
    }

    /**
     * @see org.apache.maven.doxia.sink.SinkAdapter#figureCaption_()
     */
    public void figureCaption_()
    {
        write( "\"" );
    }

    /**
     * @see org.apache.maven.doxia.sink.SinkAdapter#figureGraphics(java.lang.String)
     */
    public void figureGraphics( String name )
    {
        write( " src=\"" + name + "\"" );
    }

    
    public void anchor( String name )
    {
        if ( !headFlag )
        {
            if (StringUtils.isEmpty( name ))
            {
                return;
            }
            write( "<a name=\"" + name + "\">" );
        }
    }

    public void anchor_()
    {
        if ( !headFlag )
        {
            write( "</a>" );
        }
    }

    public void link( String name )
    {
        if ( !headFlag )
        {
            write( "<a href=\"" + name + "\">" );
        }
    }

    public void link( String name, String target )
    {
        if ( !headFlag )
        {
            write( "<a href=\"" + name + "\" target=\"" + target + "\">" );
        }
    }

    public void link_()
    {
        if ( !headFlag )
        {
            write( "</a>" );
        }
    }

    public void italic()
    {
        if ( !headFlag )
        {
            write( "<i>" );
        }
    }

    public void italic_()
    {
        if ( !headFlag )
        {
            write( "</i>" );
        }
    }

    public void bold()
    {
        if ( !headFlag )
        {
            write( "<b>" );
        }
    }

    public void bold_()
    {
        if ( !headFlag )
        {
            write( "</b>" );
        }
    }

    public void monospaced()
    {
        if ( !headFlag )
        {
            write( "<tt>" );
        }
    }

    public void monospaced_()
    {
        if ( !headFlag )
        {
            write( "</tt>" );
        }
    }

    public void lineBreak()
    {
        if ( headFlag )
        {
            buffer.append( '\n' );
        }
        else
        {
            write( "<br />" );
        }
    }

    public void nonBreakingSpace()
    {
        if ( headFlag )
        {
            buffer.append( ' ' );
        }
        else
        {
            write( "&#160;" );
        }
    }

    public void text( String text )
    {
        if ( headFlag )
        {
            buffer.append( text );
        }
        else
        {
            if ( verbatimFlag )
            {
                verbatimContent( text );
            }
            else
            {
                content( text );
            }
        }
    }

    public void rawText( String text )
    {
        write( text );
    }

    // ----------------------------------------------------------------------
    //
    // ----------------------------------------------------------------------

    protected void write( String text )
    {
        String relativePathToBasedir = renderingContext.getRelativePath();

        if ( relativePathToBasedir != null )
        {
            text = StringUtils.replace( text, "$relativePath", relativePathToBasedir );
        }
        else
        {
            text = StringUtils.replace( text, "$relativePath", "." );
        }

        writer.write( text );
    }

    protected String directiveValue( String key )
    {
        return directives.get( key );
    }

    protected void directive( String key )
    {
        write( directives.get( key ) );
    }

    protected void content( String text )
    {
        write( escapeHTML( text ) );
    }

    protected void verbatimContent( String text )

    {
        write( escapeHTML( text ) );
    }

    public static String escapeHTML( String text )
    {
        return HtmlTools.escapeHTML( text );
    }

    public static String encodeFragment( String text )
    {
        return encodeURL( StructureSink.linkToKey( text ) );
    }

    public static String encodeURL( String text )
    {
        return HtmlTools.encodeURL( text );
    }

    public void flush()
    {
        writer.flush();
    }

    public void close()
    {
        writer.close();
    }

    public RenderingContext getRenderingContext()
    {
        return renderingContext;
    }
}
