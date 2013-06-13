/**
 * The FreeBSD Copyright
 * Copyright 1994-2008 The FreeBSD Project. All rights reserved.
 * Copyright (C) 2013 Philip Helger ph[at]phloc[dot]com
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *    1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE FREEBSD PROJECT ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE FREEBSD PROJECT OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation
 * are those of the authors and should not be interpreted as representing
 * official policies, either expressed or implied, of the FreeBSD Project.
 */
package com.helger.as2lib.processor.exception;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.as2lib.exception.OpenAS2Exception;
import com.helger.as2lib.processor.IProcessor;
import com.phloc.commons.io.streams.NonBlockingStringWriter;

public class ProcessorException extends OpenAS2Exception
{
  private final IProcessor m_aProcessor;
  private List <Throwable> m_aCauses;

  public ProcessorException (final IProcessor aProcessor)
  {
    m_aProcessor = aProcessor;
  }

  public IProcessor getProcessor ()
  {
    return m_aProcessor;
  }

  @Nonnull
  public List <Throwable> getCauses ()
  {
    if (m_aCauses == null)
      m_aCauses = new ArrayList <Throwable> ();
    return m_aCauses;
  }

  public void setCauses (@Nullable final List <Throwable> aCauses)
  {
    m_aCauses = aCauses;
  }

  @Override
  public String getMessage ()
  {
    final NonBlockingStringWriter aStrWriter = new NonBlockingStringWriter ();
    final PrintWriter aWriter = new PrintWriter (aStrWriter);
    aWriter.print (super.getMessage ());
    for (final Throwable e : getCauses ())
    {
      aWriter.println ();
      e.printStackTrace (aWriter);
    }
    aWriter.flush ();
    return aStrWriter.getAsString ();
  }
}
